package com.gongbaek.android.presentation.ui.grouproom.screen

import com.gongbaek.android.domain.model.GroupRoom
import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

class GroupRoomContract {
    data class State(
        val groupRoomLoadState: UiLoadState = UiLoadState.Idle,
        val groupRoom: GroupRoom = GroupRoom(),
        val inputComment: String = "",
        val commentState: UiLoadState = UiLoadState.Idle,
        val commentReportState: UiLoadState = UiLoadState.Idle,
        val showCommentReportDialog: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data class UpdateInputComment(val inputComment: String) : Event()
        data object OnCommentRefreshClick : Event()
        data object OnCommentReportClick : Event()
        data object DismissCommentReport : Event()
        data class ConfirmCommentReport(val commentId: Int) : Event()
        data object ResetCommentReportState : Event()
        data object OnCommentPostClick : Event()
        data class OnCommentDeleteClick(val commentId: Int) : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateBack : SideEffect
    }
}
