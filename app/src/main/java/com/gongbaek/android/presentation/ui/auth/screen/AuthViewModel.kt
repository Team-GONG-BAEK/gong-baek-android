package com.gongbaek.android.presentation.ui.auth.screen

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.gongbaek.android.data.remote.util.HttpResponseException
import com.gongbaek.android.domain.model.SignUpInfo
import com.gongbaek.android.domain.type.GenderType
import com.gongbaek.android.domain.type.MbtiFirstLetterType
import com.gongbaek.android.domain.type.MbtiFourthLetterType
import com.gongbaek.android.domain.type.MbtiSecondLetterType
import com.gongbaek.android.domain.type.MbtiThirdLetterType
import com.gongbaek.android.domain.usecase.RequestEmailVerificationUseCase
import com.gongbaek.android.domain.usecase.RequestSignUpUseCase
import com.gongbaek.android.domain.usecase.SearchMajorsUseCase
import com.gongbaek.android.domain.usecase.SearchUniversitiesUseCase
import com.gongbaek.android.domain.usecase.SetTokenUseCase
import com.gongbaek.android.domain.usecase.ValidateNicknameUseCase
import com.gongbaek.android.domain.usecase.VerifyEmailCodeUseCase
import com.gongbaek.android.presentation.ui.auth.state.EmailVerificationState
import com.gongbaek.android.presentation.ui.auth.state.EmailVerificationStep
import com.gongbaek.android.presentation.ui.auth.state.EnterTimeTableState
import com.gongbaek.android.presentation.ui.auth.state.MbtiState
import com.gongbaek.android.presentation.ui.auth.state.NicknameGenderState
import com.gongbaek.android.presentation.ui.auth.state.SelectProfileState
import com.gongbaek.android.presentation.ui.auth.state.SelfIntroductionState
import com.gongbaek.android.presentation.util.base.BaseViewModel
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.timetable.convertToTimeTable
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
    private val requestSignUpUseCase: RequestSignUpUseCase,
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
            is AuthContract.Event.ClearUniversity -> clearUniversitySearchQuery()
            is AuthContract.Event.MajorSearchQueryChanged -> updateMajorSearchQuery(event.query)
            is AuthContract.Event.MajorSearchClicked -> searchMajors()
            is AuthContract.Event.MajorSelected -> updateSelectedMajor(event.major)
            is AuthContract.Event.ClearMajor -> clearMajorSearchQuery()
            is AuthContract.Event.EnterYearSelected -> updateEnterYear(event.enterYear)

            // EmailVerification Event
            is AuthContract.Event.EmailChanged -> updateEmail(event.email)
            is AuthContract.Event.VerificationCodeRequested -> handleVerificationCodeRequested()
            is AuthContract.Event.VerificationCodeChanged -> updateVerificationCode(event.code)
            is AuthContract.Event.VerificationCodeSubmitted -> handleVerificationCodeSubmitted()
            is AuthContract.Event.ClearEmailVerification -> clearEmailVerificationState()

            // NicknameGender Event
            is AuthContract.Event.NicknameChanged -> updateNickname(event.nickname)
            is AuthContract.Event.GenderSelected -> updateSelectedGender(event.gender)
            is AuthContract.Event.ValidateNickname -> handleNicknameValidation()
            is AuthContract.Event.ClearNicknameGender -> clearNicknameGenderState()

            // SelectProfile Event
            is AuthContract.Event.ProfileImageSelected -> updateProfileImage(event.profileImageIndex)
            is AuthContract.Event.ClearProfileImage -> clearSelectProfileState()

            // Mbti Event
            is AuthContract.Event.MbtiFirstOptionSelected -> updateMbtiFirstOption(event.option)
            is AuthContract.Event.MbtiSecondOptionSelected -> updateMbtiSecondOption(event.option)
            is AuthContract.Event.MbtiThirdOptionSelected -> updateMbtiThirdOption(event.option)
            is AuthContract.Event.MbtiFourthOptionSelected -> updateMbtiFourthOption(event.option)
            is AuthContract.Event.ClearMbti -> clearMbtiState()

            // SelfIntroduction Event
            is AuthContract.Event.SelfIntroductionChanged -> updateSelfIntroduction(event.selfIntroduction)
            is AuthContract.Event.ClearSelfIntroduction -> clearSelfIntroductionState()

            // EnterTimeTable Event
            is AuthContract.Event.TimeSlotSelectionChanged -> updateTimeSlotSelectionChanged(event.day, event.timeSlots)
            is AuthContract.Event.ClearTimeTable -> clearTimeTableState()
            is AuthContract.Event.RequestSingUp -> requestSingUp()
        }
    }

    fun sendSideEffect(sideEffect: AuthContract.SideEffect) = setSideEffect(sideEffect)

    private fun updateUniversitySearchQuery(query: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                universitySearchQuery = query
            )
        )
    }

    private fun searchUniversities() = viewModelScope.launch {
        setState { copy(loadState = UiLoadState.Loading) }
        searchUniversitiesUseCase(currentState.academicInfoState.universitySearchQuery.trim())
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
                university = if (currentState.academicInfoState.university == university) "" else university
            )
        )
    }

    private fun clearUniversitySearchQuery() = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                university = "",
                universitySearchQuery = "",
                searchedUniversities = null
            )
        )
    }

    private fun updateMajorSearchQuery(query: String) = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                majorSearchQuery = query.trim()
            )
        )
    }

    private fun searchMajors() = viewModelScope.launch {
        setState { copy(loadState = UiLoadState.Loading) }
        searchMajorsUseCase(
            universityName = currentState.academicInfoState.university,
            majorName = currentState.academicInfoState.majorSearchQuery.trim()
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
                major = if (currentState.academicInfoState.major == major) "" else major
            )
        )
    }

    private fun clearMajorSearchQuery() = setState {
        copy(
            academicInfoState = currentState.academicInfoState.copy(
                major = "",
                majorSearchQuery = "",
                searchedMajors = null
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
                            verificationCodeMessage = MESSAGE_CODE_EXPIRED
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
                        emailMessage = ERROR_INVALID_EMAIL
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
                                emailMessage = MESSAGE_CODE_SENT,
                                isTimerRunning = true,
                                timeLeft = EMAIL_VERIFICATION_TIME_LIMIT,
                                isVerificationCodeEnabled = true
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
                                emailMessage = ERROR_INVALID_EMAIL
                            )
                        )
                    }
                }
            )
        }
    }

    private fun handleVerificationCodeSubmitted() {
        val email = currentState.emailVerificationState.email

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
                                verificationCodeMessage = MESSAGE_CODE_SUCCESS,
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
                                verificationCodeMessage = ERROR_CODE_MISMATCH
                            )
                        )
                    }
                }
            )
        }
    }

    private fun clearEmailVerificationState() = setState {
        copy(
            emailVerificationState = EmailVerificationState()
        )
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

    private fun validateNicknameLocally(nickname: String): String? {
        val regex = Regex("^[a-zA-Z가-힣]{2,8}$")
        return if (regex.matches(nickname)) null else ERROR_NICKNAME_VALIDATION_MESSAGE
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
                    if (exception is HttpResponseException) {
                        when (exception.code) {
                            ERROR_CODE_DUPLICATE_NICKNAME -> {
                                setState {
                                    copy(
                                        nicknameGenderState = currentState.nicknameGenderState.copy(
                                            nicknameErrorMessage = ERROR_NICKNAME_DUPLICATE_MESSAGE
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun clearNicknameGenderState() = setState {
        copy(
            nicknameGenderState = NicknameGenderState()
        )
    }

    private fun updateProfileImage(profileImageIndex: Int) = setState {
        copy(
            selectProfileState = currentState.selectProfileState.copy(
                profileImageIndex = profileImageIndex
            )
        )
    }

    private fun clearSelectProfileState() = setState {
        copy(
            selectProfileState = SelectProfileState()
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

    private fun clearMbtiState() = setState {
        copy(
            mbtiState = MbtiState()
        )
    }

    private fun updateSelfIntroduction(selfIntroduction: String) = setState {
        copy(
            selfIntroductionState = currentState.selfIntroductionState.copy(
                selfIntroduction = selfIntroduction
            )
        )
    }

    private fun clearSelfIntroductionState() = setState {
        copy(
            selfIntroductionState = SelfIntroductionState()
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

    private fun clearTimeTableState() = setState {
        copy(
            enterTimeTableState = EnterTimeTableState()
        )
    }

    private fun buildSignUpInfo(): SignUpInfo =
        SignUpInfo(
            platform = "KAKAO",
            university = currentState.academicInfoState.university,
            major = currentState.academicInfoState.major,
            enterYear = currentState.academicInfoState.enterYear,
            email = currentState.emailVerificationState.email,
            nickname = currentState.nicknameGenderState.nickname,
            gender = GenderType.fromDescription(currentState.nicknameGenderState.gender).name,
            profileImage = currentState.selectProfileState.profileImageIndex ?: DEFAULT_PROFILE_IMAGE_INDEX,
            mbti = MbtiFirstLetterType.fromDescription(currentState.mbtiState.firstLetter).name +
                MbtiSecondLetterType.fromDescription(currentState.mbtiState.secondLetter).name +
                MbtiThirdLetterType.fromDescription(currentState.mbtiState.thirdLetter).name +
                MbtiFourthLetterType.fromDescription(currentState.mbtiState.forthLetter).name,
            introduction = currentState.selfIntroductionState.selfIntroduction,
            timeTable = convertToTimeTable(currentState.enterTimeTableState.selectedTimeSlotsByDay)
        )

    private fun requestSingUp() =
        viewModelScope.launch {
            setState { copy(loadState = UiLoadState.Loading) }
            requestSignUpUseCase(signUpInfo = buildSignUpInfo())
                .fold(
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

    companion object {
        // Email Verification Messages
        private const val ERROR_INVALID_EMAIL = "잘못된 이메일입니다. 다시 입력해주세요."
        private const val MESSAGE_CODE_SENT = "인증코드를 발송했습니다."
        private const val MESSAGE_CODE_EXPIRED = "유효기간이 만료되었습니다. 코드를 다시 받아주세요."
        private const val MESSAGE_CODE_SUCCESS = "코드 인증이 완료되었습니다."
        private const val ERROR_CODE_MISMATCH = "잘못된 코드입니다. 다시 입력해주세요."

        // Nickname Validation Messages
        private const val ERROR_NICKNAME_VALIDATION_MESSAGE = "최소 2자 이상 입력해주세요."
        private const val ERROR_NICKNAME_DUPLICATE_MESSAGE = "중복된 닉네임입니다. 다시 입력해주세요."
        private const val ERROR_CODE_DUPLICATE_NICKNAME = 4092

        // Timer
        private const val EMAIL_VERIFICATION_TIME_LIMIT = 180

        private const val DEFAULT_PROFILE_IMAGE_INDEX: Int = 0
    }
}
