package com.sopt.gongbaek.presentation.ui.auth.screen

import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.presentation.ui.auth.state.AcademicInfoState
import com.sopt.gongbaek.presentation.ui.auth.state.EmailVerificationState
import com.sopt.gongbaek.presentation.ui.auth.state.NicknameGenderState
import com.sopt.gongbaek.presentation.ui.auth.state.SelectProfileState
import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class AuthContract {
    data class State(
        val loadState: UiLoadState = UiLoadState.Idle,
        val academicInfoState: AcademicInfoState = AcademicInfoState(),
        val emailVerificationState: EmailVerificationState = EmailVerificationState(),
        val nicknameGenderState: NicknameGenderState = NicknameGenderState(),
        val selectProfileState: SelectProfileState = SelectProfileState(),
        val userInfo: UserInfo = UserInfo(),
        val energyDirectionOptions: String = "",
        val informationGatheringOptions: String = "",
        val decisionMakingOptions: String = "",
        val lifestyleOrientationOptions: String = "",
        val selectedTimeSlotsByDay: Map<String, List<Int>> = emptyMap()
    ) : UiState

    sealed class Event : UiEvent {
        data class UniversitySearchQueryChanged(val query: String) : Event()
        data object UniversitySearchClicked : Event()
        data class UniversitySelected(val university: String) : Event()
        data class MajorSearchQueryChanged(val query: String) : Event()
        data object MajorSearchClicked : Event()
        data class MajorSelected(val major: String) : Event()
        data class EnterYearSelected(val enterYear: Int) : Event()

        data class EmailChanged(val email: String) : Event()
        data object VerificationCodeRequested : Event()
        data class VerificationCodeChanged(val code: String) : Event()
        data object VerificationCodeSubmitted : Event()

        data class NicknameChanged(val nickname: String) : Event()
        data class GenderSelected(val gender: String) : Event()
        data object ValidateNickname : Event()

        data class ProfileImageSelected(val profileImageIndex: Int) : Event()
        data class OnEnergyDirectionOptionSelected(val option: String) : Event()
        data class OnInformationGatheringOptionSelected(val option: String) : Event()
        data class OnDecisionMakingOptionSelected(val option: String) : Event()
        data class OnLifestyleOrientationOptionSelected(val option: String) : Event()
        data class OnSelfIntroductionChanged(val selfIntroduction: String) : Event()
        data class OnTimeSlotSelectionChange(val day: String, val timeSlots: List<Int>) : Event()
        data object SubmitUserInfo : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateBack : SideEffect
        data object NavigateAcademicInfo : SideEffect
        data object NavigateUnivSearch : SideEffect
        data object NavigateMajorSearch : SideEffect
        data object NavigateEmailVerification : SideEffect
        data object NavigateNicknameGender : SideEffect
        data object NavigateSelectProfile : SideEffect
        data object NavigateMbti : SideEffect
        data object NavigateSelfIntroduction : SideEffect
        data object NavigateEnterTimetable : SideEffect
        data object NavigateCompleteAuth : SideEffect
        data object NavigateHome : SideEffect

        data object NavigateGapTimetable : SideEffect
    }
}
