package com.sopt.gongbaek.presentation.ui.auth.screen

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.domain.usecase.GetSearchMajorsResultUseCase
import com.sopt.gongbaek.domain.usecase.GetSearchUniversitiesResultUseCase
import com.sopt.gongbaek.domain.usecase.RegisterUserInfoUseCase
import com.sopt.gongbaek.domain.usecase.SetTokenUseCase
import com.sopt.gongbaek.domain.usecase.ValidateNicknameUseCase
import com.sopt.gongbaek.presentation.ui.auth.state.EmailVerificationStep
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.extension.createMbti
import com.sopt.gongbaek.presentation.util.extension.isCompleteKorean
import com.sopt.gongbaek.presentation.util.extension.isKoreanChar
import com.sopt.gongbaek.presentation.util.timetable.convertToTimeTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getSearchUniversitiesResultUseCase: GetSearchUniversitiesResultUseCase,
    private val getSearchMajorsResultUseCase: GetSearchMajorsResultUseCase,
    private val registerUserInfoUseCase: RegisterUserInfoUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val validateNicknameUseCase: ValidateNicknameUseCase
) : BaseViewModel<AuthContract.State, AuthContract.Event, AuthContract.SideEffect>() {

    private var timerJob: Job? = null

    override fun createInitialState(): AuthContract.State = AuthContract.State()

    override suspend fun handleEvent(event: AuthContract.Event) {
        when (event) {
            // AcademicInfo Event
            is AuthContract.Event.UniversitySearchQueryChanged -> updateUniversitySearchQuery(event.query)
            is AuthContract.Event.UniversitySearchClicked -> fetchUniversities()
            is AuthContract.Event.UniversitySelected -> updateSelectedUniversity(event.university)
            is AuthContract.Event.MajorSearchQueryChanged -> updateMajorSearchQuery(event.query)
            is AuthContract.Event.MajorSearchClicked -> fetchMajors()
            is AuthContract.Event.MajorSelected -> updateSelectedMajor(event.major)
            is AuthContract.Event.EnterYearSelected -> updateEnterYear(event.enterYear)

            // EmailVerification Event
            is AuthContract.Event.EmailChanged -> updateEmail(event.email)
            is AuthContract.Event.VerificationCodeRequested -> handleVerificationCodeRequested()
            is AuthContract.Event.VerificationCodeChanged -> updateVerificationCode(event.code)
            is AuthContract.Event.VerificationCodeSubmitted -> handleVerificationCodeSubmitted()

            // NicknameGender Event
            is AuthContract.Event.NicknameChanged -> updateNickname(event.nickname)
            is AuthContract.Event.GenderSelected -> updateSelectedGender(event.gender)
            is AuthContract.Event.ValidateNickname -> handleNicknameValidation()

            // SelectProfile Event
            is AuthContract.Event.ProfileImageSelected -> updateProfileImage(event.profileImageIndex)

            // Mbti Event
            is AuthContract.Event.EnergyOptionSelected -> updateMbtiEnergy(event.energy)
            is AuthContract.Event.PerceptionOptionSelected -> updateMbtiPerception(event.perception)
            is AuthContract.Event.DecisionOptionSelected -> updateMbtiDecision(event.decision)
            is AuthContract.Event.LifestyleOptionSelected -> updateMbtiLifestyle(event.lifestyle)

            is AuthContract.Event.OnSelfIntroductionChanged -> updateUserInfo { copy(introduction = event.selfIntroduction) }
            is AuthContract.Event.OnTimeSlotSelectionChange -> {
                val timeSlotsByDay = currentState.selectedTimeSlotsByDay.toMutableMap()
                timeSlotsByDay[event.day] = event.timeSlots
                setState { copy(selectedTimeSlotsByDay = timeSlotsByDay) }
                updateUserInfo { copy(timeTable = convertToTimeTable(timeSlotsByDay)) }
            }

            is AuthContract.Event.SubmitUserInfo -> submitUserInfo()
        }
    }


    private fun submitUserInfo() =
        viewModelScope.launch {
            setState { copy(loadState = UiLoadState.Loading) }
            registerUserInfoUseCase(currentState.userInfo).fold(
                onSuccess = { userAuth ->
                    setState { copy(loadState = UiLoadState.Success) }
                    setSideEffect(AuthContract.SideEffect.NavigateCompleteAuth)
                    setTokenUseCase(userAuth.accessToken, userAuth.refreshToken)
                },
                onFailure = {
                    setState { copy(loadState = UiLoadState.Error) }
                }
            )
        }


    private fun updateUserInfo(update: UserInfo.() -> UserInfo) =
        setState { copy(userInfo = userInfo.update()) }

    private fun updateUniversitySearchQuery(query: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                universitySearchQuery = query
            )
        )
    }

    private fun fetchUniversities() = viewModelScope.launch {
        setState { copy(loadState = UiLoadState.Loading) }
        // TODO usecase 네이밍 수정
        getSearchUniversitiesResultUseCase(currentState.academicInfoState.universitySearchQuery).fold(
            onSuccess = { universities ->
                setState {
                    copy(
                        academicInfoState = currentState.academicInfoState.copy(
                            searchedUniversities = universities.universities
                        )
                    )
                }
            },
            onFailure = {
                setState { copy(loadState = UiLoadState.Error) }
            }
        )
    }

    private fun updateSelectedUniversity(university: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                university = university
            )
        )
    }

    private fun updateMajorSearchQuery(query: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                majorSearchQuery = query
            )
        )
    }

    private fun fetchMajors() = viewModelScope.launch {
        setState { copy(loadState = UiLoadState.Loading) }
        getSearchMajorsResultUseCase(
            // TODO usecase 네이밍 수정
            universityName = currentState.academicInfoState.university,
            majorName = currentState.academicInfoState.majorSearchQuery
        ).fold(
            onSuccess = { majors ->
                setState {
                    copy(
                        academicInfoState = currentState.academicInfoState.copy(
                            searchedMajors = majors.majors
                        )
                    )
                }
            },
            onFailure = {
                setState { copy(loadState = UiLoadState.Error) }
            }
        )
    }

    private fun updateSelectedMajor(major: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                major = major
            )
        )
    }

    private fun updateEnterYear(enterYear: Int) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                enterYear = enterYear
            )
        )
    }

    private fun updateEmail(email: String) = setState {
        copy(
            emailVerificationState = currentState.emailVerificationState.copy(
                email = email
            )
        )
    }

    private fun updateVerificationCode(code: String) = setState {
        copy(
            emailVerificationState = currentState.emailVerificationState.copy(
                verificationCode = code
            )
        )
    }

    private fun startTimer() {
        if (timerJob?.isActive == true) return

        timerJob = viewModelScope.launch {
            var timeLeft = currentState.emailVerificationState.timeLeft
            while (isActive && timeLeft > 0) {
                delay(1000)
                timeLeft -= 1
                setState {
                    copy(
                        emailVerificationState = currentState.emailVerificationState.copy(
                            timeLeft = timeLeft
                        )
                    )
                }
            }

            if (timeLeft == 0) {
                setState {
                    copy(
                        emailVerificationState = emailVerificationState.copy(
                            isTimerRunning = false,
                            step = EmailVerificationStep.VERIFICATION_FAILED,
                            verificationCodeMessage = "유효시간이 만료되었습니다. 인증코드를 다시 요청해주세요."
                        )
                    )
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null

        setState {
            copy(
                emailVerificationState = emailVerificationState.copy(
                    isTimerRunning = false
                )
            )
        }
    }

    private fun handleVerificationCodeRequested() {
        if (!Patterns.EMAIL_ADDRESS.matcher(currentState.emailVerificationState.email).matches()) {
            setState {
                copy(
                    emailVerificationState = emailVerificationState.copy(
                        step = EmailVerificationStep.REQUEST_FAILED,
                        emailMessage = "잘못된 이메일입니다. 다시 입력해주세요."
                    )
                )
            }
            return
        }

        setState {
            copy(
                emailVerificationState = emailVerificationState.copy(
                    step = EmailVerificationStep.REQUESTED,
                    emailMessage = "인증코드를 발송했습니다.",
                    isTimerRunning = true,
                )
            )
        }
        startTimer()
    }

    private fun handleVerificationCodeSubmitted() =
        when {
            currentState.emailVerificationState.email.isEmpty() -> setState {
                copy(
                    emailVerificationState = emailVerificationState.copy(
                        step = EmailVerificationStep.VERIFICATION_FAILED,
                        verificationCodeMessage = "이메일을 먼저 입력해주세요."
                    )
                )
            }

            currentState.emailVerificationState.verificationCode != "123456" -> setState {
                copy(
                    emailVerificationState = emailVerificationState.copy(
                        step = EmailVerificationStep.VERIFICATION_FAILED,
                        verificationCodeMessage = "인증코드가 일치하지 않습니다."
                    )
                )
            }

            else -> {
                setState {
                    copy(
                        emailVerificationState = emailVerificationState.copy(
                            step = EmailVerificationStep.VERIFIED,
                            verificationCodeMessage = "인증이 완료되었습니다.",
                            isTimerRunning = false
                        )
                    )
                }
                stopTimer()
            }
        }

    private fun updateNickname(nickname: String) = setState {
        copy(
            nicknameGenderState = currentState.nicknameGenderState.copy(
                nickname = nickname
            )
        )
    }

    private fun updateSelectedGender(gender: String) = setState {
        copy(
            nicknameGenderState = currentState.nicknameGenderState.copy(
                gender = gender
            )
        )
    }

    private fun validateNicknameLocally(nickname: String): String? =
        when {
            nickname.length < 2 -> "닉네임은 최소 2글자 이상이어야 합니다."
            !nickname.all { it.isKoreanChar() } -> "닉네임은 한글만 사용할 수 있습니다."
            !nickname.all { it.isCompleteKorean() } -> "닉네임은 완성된 한글만 사용할 수 있습니다."
            else -> null
        }

    private fun handleNicknameValidation() {
        val nickname = currentState.nicknameGenderState.nickname
        val localValidationError = validateNicknameLocally(nickname)

        if (localValidationError != null) {
            setState {
                copy(
                    nicknameGenderState = currentState.nicknameGenderState.copy(
                        nicknameErrorMessage = localValidationError
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            validateNicknameUseCase(nickname).fold(
                onSuccess = {
                    sendSideEffect(AuthContract.SideEffect.NavigateSelectProfile)
                },
                onFailure = { exception ->
                    if (exception.message == ERROR_CODE_DUPLICATE_NICKNAME) {
                        setState {
                            copy(
                                nicknameGenderState = currentState.nicknameGenderState.copy(
                                    nicknameErrorMessage = NICKNAME_VALIDATION_ERROR_MESSAGE
                                )
                            )
                        }
                    }
                }
            )
        }
    }

    private fun updateProfileImage(profileImageIndex: Int) = setState {
        copy(
            selectProfileState = currentState.selectProfileState.copy(
                profileImageIndex = profileImageIndex
            )
        )
    }

    private fun updateMbtiEnergy(energy: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                energy = energy
            )
        )
    }

    private fun updateMbtiPerception(perception: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                perception = perception
            )
        )
    }

    private fun updateMbtiDecision(decision: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                decision = decision
            )
        )
    }

    private fun updateMbtiLifestyle(lifestyle: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                lifestyle = lifestyle
            )
        )
    }

    companion object {
        private const val NICKNAME_VALIDATION_ERROR_MESSAGE = "중복된 닉네임입니다. 다시 입력해주세요."
        private const val ERROR_CODE_DUPLICATE_NICKNAME = "HTTP 409 "
    }
}
