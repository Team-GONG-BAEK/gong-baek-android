package com.sopt.gongbaek.presentation.ui.groupdetail.screen

import com.sopt.gongbaek.domain.model.GroupDetail
import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class GroupDetailContract {
    data class State(
        val groupDetailLoadState: UiLoadState = UiLoadState.Idle,
        val groupDetail: GroupDetail = GroupDetail(),
        val inputComment: String = "",
        val groupApplyState: UiLoadState = UiLoadState.Idle,
        val groupApplyStatusCode: Int? = null,
        val groupCancelState: UiLoadState = UiLoadState.Idle,
        val groupDeleteState: UiLoadState = UiLoadState.Idle,
        val commentState: UiLoadState = UiLoadState.Idle
    ) : UiState

    sealed class Event : UiEvent {
        data object OnGroupInfoTabClick : Event()
        data object OnApplyClick : Event()
        data object ResetApplyState : Event()
        data object OnCancelClick : Event()
        data object ResetCancelState : Event()
        data object ShowDeleteDialog : Event()
        data object OnDeleteClick : Event()
        data object ResetDeleteState : Event()
        data object OnCommentTabClick : Event()
        data class UpdateInputComment(val inputComment: String) : Event()
        data object OnCommentRefreshClick : Event()
        data object OnCommentPostClick : Event()
        data class OnCommentDeleteClick(val commentId: Int) : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateBack : SideEffect
        data class NavigateGroupRoom(val groupId: Int, val groupCycle: String) : SideEffect
        data object NavigateMyPage : SideEffect
    }
}
