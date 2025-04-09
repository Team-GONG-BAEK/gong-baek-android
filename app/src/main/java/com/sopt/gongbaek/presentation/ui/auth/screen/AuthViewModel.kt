package com.sopt.gongbaek.presentation.ui.auth.screen

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.usecase.SearchMajorsUseCase
import com.sopt.gongbaek.domain.usecase.SearchUniversitiesUseCase
import com.sopt.gongbaek.domain.usecase.RegisterUserInfoUseCase
import com.sopt.gongbaek.domain.usecase.RequestEmailVerificationUseCase
import com.sopt.gongbaek.domain.usecase.SetTokenUseCase
import com.sopt.gongbaek.domain.usecase.ValidateNicknameUseCase
import com.sopt.gongbaek.domain.usecase.VerifyEmailCodeUseCase
import com.sopt.gongbaek.presentation.ui.auth.state.EmailVerificationStep
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.extension.isCompleteKorean
import com.sopt.gongbaek.presentation.util.extension.isKoreanChar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val searchUniversitiesUseCase: SearchUniversitiesUseCase,
    private val searchMajorsUseCase: SearchMajorsUseCase,
    private val requestEmailVerificationUseCase: RequestEmailVerificationUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
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
            is AuthContract.Event.UniversitySearchClicked -> searchUniversities()
            is AuthContract.Event.UniversitySelected -> updateSelectedUniversity(event.university)
            is AuthContract.Event.MajorSearchQueryChanged -> updateMajorSearchQuery(event.query)
            is AuthContract.Event.MajorSearchClicked -> searchMajors()
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
            is AuthContract.Event.MbtiFirstOptionSelected -> updateMbtiFirstOption(event.option)
            is AuthContract.Event.MbtiSecondOptionSelected -> updateMbtiSecondOption(event.option)
            is AuthContract.Event.MbtiThirdOptionSelected -> updateMbtiThirdOption(event.option)
            is AuthContract.Event.MbtiFourthOptionSelected -> updateMbtiFourthOption(event.option)

            // SelfIntroduction Event
            is AuthContract.Event.SelfIntroductionChanged -> updateSelfIntroduction(event.selfIntroduction)

            // EnterTimeTable Event
            is AuthContract.Event.TimeSlotSelectionChanged -> updateTimeSlotSelectionChanged(event.day, event.timeSlots)

            // TODO 전송로직 서버연결 하며 수정 예정
//            is AuthContract.Event.SubmitUserInfo -> submitUserInfo()
        }
    }

    fun sendSideEffect(sideEffect: AuthContract.SideEffect) = setSideEffect(sideEffect)

//    private fun submitUserInfo() =
//        viewModelScope.launch {
//            setState { copy(loadState = UiLoadState.Loading) }
//            registerUserInfoUseCase(currentState.userInfo).fold(
//                onSuccess = { userAuth ->
//                    setState { copy(loadState = UiLoadState.Success) }
//                    setSideEffect(AuthContract.SideEffect.NavigateCompleteAuth)
//                    setTokenUseCase(userAuth.accessToken, userAuth.refreshToken)
//                },
//                onFailure = {
//                    setState { copy(loadState = UiLoadState.Error) }
//                }
//            )
//        }

    private fun updateUniversitySearchQuery(query: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                universitySearchQuery = query
            )
        )
    }

    private fun searchUniversities() = viewModelScope.launch {
        setState { copy(loadState = UiLoadState.Loading) }
        searchUniversitiesUseCase(currentState.academicInfoState.universitySearchQuery)
            .fold(
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

    private fun searchMajors() = viewModelScope.launch {
        setState { copy(loadState = UiLoadState.Loading) }
        searchMajorsUseCase(
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
        val email = currentState.emailVerificationState.email

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

        viewModelScope.launch {
            requestEmailVerificationUseCase(
                email = email,
                schoolName = currentState.academicInfoState.university
            ).fold(
                onSuccess = {
                    stopTimer()
                    setState {
                        copy(
                            emailVerificationState = emailVerificationState.copy(
                                step = EmailVerificationStep.REQUESTED,
                                emailMessage = "인증코드를 발송했습니다.",
                                isTimerRunning = true,
                                timeLeft = 180
                            )
                        )
                    }
                    startTimer()
                },
                onFailure = {
                    setState {
                        copy(
                            emailVerificationState = emailVerificationState.copy(
                                step = EmailVerificationStep.REQUEST_FAILED,
                                emailMessage = "잘못된 이메일입니다. 다시 입력해주세요."
                            )
                        )
                    }
                }
            )
        }
    }

    private fun handleVerificationCodeSubmitted() {
        val email = currentState.emailVerificationState.email

        if (email.isEmpty()) {
            setState {
                copy(
                    emailVerificationState = emailVerificationState.copy(
                        step = EmailVerificationStep.VERIFICATION_FAILED,
                        verificationCodeMessage = "이메일을 먼저 입력해주세요."
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            verifyEmailCodeUseCase(
                email = email,
                schoolName = currentState.academicInfoState.university,
                code = currentState.emailVerificationState.verificationCode
            ).fold(
                onSuccess = {
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
                },
                onFailure = {
                    setState {
                        copy(
                            emailVerificationState = emailVerificationState.copy(
                                step = EmailVerificationStep.VERIFICATION_FAILED,
                                verificationCodeMessage = "인증코드가 일치하지 않습니다."
                            )
                        )
                    }
                }
            )
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

    private fun updateMbtiFirstOption(option: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                firstLetter = option
            )
        )
    }

    private fun updateMbtiSecondOption(option: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                secondLetter = option
            )
        )
    }

    private fun updateMbtiThirdOption(option: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                thirdLetter = option
            )
        )
    }

    private fun updateMbtiFourthOption(option: String) = setState {
        copy(
            mbtiState = currentState.mbtiState.copy(
                forthLetter = option
            )
        )
    }

    private fun updateSelfIntroduction(selfIntroduction: String) = setState {
        copy(
            selfIntroductionState = currentState.selfIntroductionState.copy(
                selfIntroduction = selfIntroduction
            )
        )
    }

    private fun updateTimeSlotSelectionChanged(day: String, timeSlots: List<Int>) {
        val updatedTimeSlotsByDay = currentState.enterTimeTableState.selectedTimeSlotsByDay
            .plus(day to timeSlots)

        setState {
            copy(
                enterTimeTableState = currentState.enterTimeTableState.copy(
                    selectedTimeSlotsByDay = updatedTimeSlotsByDay
                )
            )
        }
    }

    companion object {
        private const val NICKNAME_VALIDATION_ERROR_MESSAGE = "중복된 닉네임입니다. 다시 입력해주세요."
        private const val ERROR_CODE_DUPLICATE_NICKNAME = "HTTP 409 "
    }
}
