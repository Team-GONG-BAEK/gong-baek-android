package com.sopt.gongbaek.presentation.ui.auth.screen

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.domain.type.GenderType
import com.sopt.gongbaek.domain.usecase.GetSearchMajorsResultUseCase
import com.sopt.gongbaek.domain.usecase.GetSearchUniversitiesResultUseCase
import com.sopt.gongbaek.domain.usecase.RegisterUserInfoUseCase
import com.sopt.gongbaek.domain.usecase.SetTokenUseCase
import com.sopt.gongbaek.domain.usecase.ValidateNicknameUseCase
import com.sopt.gongbaek.presentation.ui.auth.state.EmailVerificationStep
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.extension.createMbti
import com.sopt.gongbaek.presentation.util.extension.hasCompleteKoreanCharacters
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
            is AuthContract.Event.OnProfileImageSelected -> updateUserInfo { copy(profileImage = event.profileImage) }
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

            is AuthContract.Event.OnNicknameChanged -> {
                val filteredNickname = event.nickname.filter { it.isKoreanChar() }
                val isValidNickname = filteredNickname.hasCompleteKoreanCharacters(2)
                val containsIncompleteKorean = filteredNickname.any { it.isKoreanChar() && !it.isCompleteKorean() }
                updateUserInfo { copy(nickname = filteredNickname) }
                setState {
                    copy(
                        nicknameValidation = isValidNickname && !containsIncompleteKorean,
                        nicknameErrorMessage = when {
                            containsIncompleteKorean -> "완성되지 않은 글자가 포함되어 있습니다."
                            !isValidNickname -> "닉네임은 최소 2글자 이상의 완성된 한글이어야 합니다."
                            else -> null
                        }
                    )
                }
            }

            is AuthContract.Event.OnGenderSelected -> {
                val gender = GenderType.toGender(event.selectedGender)
                updateUserInfo { copy(gender = gender) }
                setState { copy(selectedGender = event.selectedGender) }
            }

            is AuthContract.Event.OnEnergyDirectionOptionSelected -> {
                setState { copy(energyDirectionOptions = event.option) }
                updateMbti()
            }

            is AuthContract.Event.OnInformationGatheringOptionSelected -> {
                setState { copy(informationGatheringOptions = event.option) }
                updateMbti()
            }

            is AuthContract.Event.OnDecisionMakingOptionSelected -> {
                setState { copy(decisionMakingOptions = event.option) }
                updateMbti()
            }

            is AuthContract.Event.OnLifestyleOrientationOptionSelected -> {
                setState { copy(lifestyleOrientationOptions = event.option) }
                updateMbti()
            }

            is AuthContract.Event.OnSelfIntroductionChanged -> updateUserInfo { copy(introduction = event.selfIntroduction) }
            is AuthContract.Event.OnTimeSlotSelectionChange -> {
                val timeSlotsByDay = currentState.selectedTimeSlotsByDay.toMutableMap()
                timeSlotsByDay[event.day] = event.timeSlots
                setState { copy(selectedTimeSlotsByDay = timeSlotsByDay) }
                updateUserInfo { copy(timeTable = convertToTimeTable(timeSlotsByDay)) }
            }

            is AuthContract.Event.SubmitUserInfo -> submitUserInfo()

            is AuthContract.Event.ValidateNickname -> {
                validateNickname(currentState.userInfo.nickname) { isValid ->
                    if (isValid) {
                        sendSideEffect(AuthContract.SideEffect.NavigateSelectProfile)
                    }
                }
            }
        }
    }

    fun sendSideEffect(sideEffect: AuthContract.SideEffect) =
        setSideEffect(sideEffect)

    private fun updateMbti() {
        val mbti = createMbti(
            firstLetter = currentState.energyDirectionOptions.takeIf { it.isNotBlank() } ?: return,
            secondLetterType = currentState.informationGatheringOptions.takeIf { it.isNotBlank() } ?: return,
            thirdLetterType = currentState.decisionMakingOptions.takeIf { it.isNotBlank() } ?: return,
            fourthLetterType = currentState.lifestyleOrientationOptions.takeIf { it.isNotBlank() } ?: return
        )
        updateUserInfo { copy(mbti = mbti) }
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

    private fun validateNickname(nickname: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = validateNicknameUseCase(nickname)
            result.fold(
                onSuccess = {
                    setState {
                        copy(
                            nicknameValidation = true,
                            nicknameErrorMessage = null
                        )
                    }
                    onResult(true)
                },
                onFailure = { exception ->
                    if (exception.message == ERROR_CODE_DUPLICATE_NICKNAME) {
                        setState {
                            copy(
                                nicknameValidation = false,
                                nicknameErrorMessage = NICKNAME_VALIDATION_ERROR_MESSAGE
                            )
                        }
                    }
                    onResult(false)
                }
            )
        }
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

    companion object {
        private const val NICKNAME_VALIDATION_ERROR_MESSAGE = "중복된 닉네임입니다. 다시 입력해주세요."
        private const val ERROR_CODE_DUPLICATE_NICKNAME = "HTTP 409 "
    }
}
