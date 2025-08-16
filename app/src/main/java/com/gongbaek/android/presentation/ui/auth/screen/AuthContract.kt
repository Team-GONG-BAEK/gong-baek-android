package com.gongbaek.android.presentation.ui.auth.screen

import com.gongbaek.android.presentation.ui.auth.state.AcademicInfoState
import com.gongbaek.android.presentation.ui.auth.state.EmailVerificationState
import com.gongbaek.android.presentation.ui.auth.state.EnterTimeTableState
import com.gongbaek.android.presentation.ui.auth.state.MbtiState
import com.gongbaek.android.presentation.ui.auth.state.NicknameGenderState
import com.gongbaek.android.presentation.ui.auth.state.SelectProfileState
import com.gongbaek.android.presentation.ui.auth.state.SelfIntroductionState
import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

class AuthContract {
    data class State(
        val loadState: UiLoadState = UiLoadState.Idle,
        val academicInfoState: AcademicInfoState = AcademicInfoState(),
        val emailVerificationState: EmailVerificationState = EmailVerificationState(),
        val nicknameGenderState: NicknameGenderState = NicknameGenderState(),
        val selectProfileState: SelectProfileState = SelectProfileState(),
        val mbtiState: MbtiState = MbtiState(),
        val selfIntroductionState: SelfIntroductionState = SelfIntroductionState(),
        val enterTimeTableState: EnterTimeTableState = EnterTimeTableState()
    ) : UiState

    sealed class Event : UiEvent {
        data class UniversitySearchQueryChanged(val query: String) : Event()
        data object UniversitySearchClicked : Event()
        data class UniversitySelected(val university: String) : Event()
        data object ClearUniversity : Event()
        data class MajorSearchQueryChanged(val query: String) : Event()
        data object MajorSearchClicked : Event()
        data class MajorSelected(val major: String) : Event()
        data object ClearMajor : Event()
        data class EnterYearSelected(val enterYear: Int) : Event()

        data class EmailChanged(val email: String) : Event()
        data object VerificationCodeRequested : Event()
        data class VerificationCodeChanged(val code: String) : Event()
        data object VerificationCodeSubmitted : Event()
        data object ClearEmailVerification : Event()

        data class NicknameChanged(val nickname: String) : Event()
        data class GenderSelected(val gender: String) : Event()
        data object ValidateNickname : Event()
        data object ClearNicknameGender : Event()

        data class ProfileImageSelected(val profileImageIndex: Int) : Event()
        data object ClearProfileImage : Event()

        data class MbtiFirstOptionSelected(val option: String) : Event()
        data class MbtiSecondOptionSelected(val option: String) : Event()
        data class MbtiThirdOptionSelected(val option: String) : Event()
        data class MbtiFourthOptionSelected(val option: String) : Event()
        data object ClearMbti : Event()

        data class SelfIntroductionChanged(val selfIntroduction: String) : Event()
        data object ClearSelfIntroduction : Event()

        data class TimeSlotSelectionChanged(val day: String, val timeSlots: List<Int>) : Event()
        data object ClearTimeTable : Event()
        data object RequestSingUp : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateBack : SideEffect
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
    }
}
