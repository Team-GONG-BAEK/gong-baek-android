package com.sopt.gongbaek.presentation.ui.auth.screen

import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.presentation.ui.auth.state.AcademicInfoState
import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class AuthContract {
    data class State(
        val loadState: UiLoadState = UiLoadState.Idle,
        val academicInfoState: AcademicInfoState = AcademicInfoState(),
        val userInfo: UserInfo = UserInfo(),
        val energyDirectionOptions: String = "",
        val informationGatheringOptions: String = "",
        val decisionMakingOptions: String = "",
        val lifestyleOrientationOptions: String = "",
        val selectedGender: String = "",
        val nicknameValidation: Boolean = false,
        val nicknameErrorMessage: String? = null,
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
        data class OnProfileImageSelected(val profileImage: Int) : Event()
        data class OnNicknameChanged(val nickname: String) : Event()
        data class OnEnergyDirectionOptionSelected(val option: String) : Event()
        data class OnInformationGatheringOptionSelected(val option: String) : Event()
        data class OnDecisionMakingOptionSelected(val option: String) : Event()
        data class OnLifestyleOrientationOptionSelected(val option: String) : Event()
        data class OnGenderSelected(val selectedGender: String) : Event()
        data class OnSelfIntroductionChanged(val selfIntroduction: String) : Event()
        data class OnTimeSlotSelectionChange(val day: String, val timeSlots: List<Int>) : Event()
        data object SubmitUserInfo : Event()
        data object ValidateNickname : Event()
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
