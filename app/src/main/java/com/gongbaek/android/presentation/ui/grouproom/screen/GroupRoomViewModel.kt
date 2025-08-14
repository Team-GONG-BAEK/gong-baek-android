package com.gongbaek.android.presentation.ui.grouproom.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gongbaek.android.domain.model.Comment
import com.gongbaek.android.domain.model.GroupRoom
import com.gongbaek.android.domain.usecase.DeleteCommentUseCase
import com.gongbaek.android.domain.usecase.GetGroupCommentsUseCase
import com.gongbaek.android.domain.usecase.LoadGroupRoomScreenUseCase
import com.gongbaek.android.domain.usecase.PostCommentUseCase
import com.gongbaek.android.domain.usecase.ReportCommentUseCase
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.util.base.BaseViewModel
import com.gongbaek.android.presentation.util.base.UiLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRoomViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadGroupRoomScreenUseCase: LoadGroupRoomScreenUseCase,
    private val getGroupCommentsUseCase: GetGroupCommentsUseCase,
    private val reportCommentUseCase: ReportCommentUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
) : BaseViewModel<GroupRoomContract.State, GroupRoomContract.Event, GroupRoomContract.SideEffect>() {

    init {
        val groupId: Int = savedStateHandle.toRoute<NavigationRoute.GroupRoom>().groupId
        val groupCycle: String = savedStateHandle.toRoute<NavigationRoute.GroupRoom>().groupCycle
        updateGroupRoom {
            copy(
                groupInfo = groupInfo.copy(
                    groupId = groupId,
                    cycle = groupCycle
                )
            )
        }
    }

    override fun createInitialState(): GroupRoomContract.State = GroupRoomContract.State()

    override suspend fun handleEvent(event: GroupRoomContract.Event) {
        when (event) {
            is GroupRoomContract.Event.UpdateInputComment -> setState {
                copy(inputComment = event.inputComment)
            }
            is GroupRoomContract.Event.OnCommentRefreshClick -> {
                getGroupComment()
            }
            is GroupRoomContract.Event.OnCommentReportClick -> {
                setState { copy(showCommentReportDialog = true) }
            }
            is GroupRoomContract.Event.DismissCommentReport -> {
                setState { copy(showCommentReportDialog = false) }
            }
            is GroupRoomContract.Event.ConfirmCommentReport -> {
                reportComment(event.commentId)
                setState { copy(showCommentReportDialog = false) }
            }
            is GroupRoomContract.Event.ResetCommentReportState -> {
                setState { copy(commentReportState = UiLoadState.Idle) }
            }
            is GroupRoomContract.Event.OnCommentPostClick -> {
                postInputComment()
            }
            is GroupRoomContract.Event.OnCommentDeleteClick -> {
                deleteComment(event.commentId)
            }
        }
    }

    fun sendSideEffect(sideEffect: GroupRoomContract.SideEffect) = setSideEffect(sideEffect)

    private fun updateGroupRoom(update: GroupRoom.() -> GroupRoom) =
        setState { copy(groupRoom = groupRoom.update()) }

    fun getGroupRoomInfo() {
        viewModelScope.launch {
            setState { copy(groupRoomLoadState = UiLoadState.Loading) }
            loadGroupRoomScreenUseCase(
                groupId = currentState.groupRoom.groupInfo.groupId,
                groupType = currentState.groupRoom.groupInfo.cycle
            ).fold(
                onSuccess = { groupRoom ->
                    setState { copy(groupRoomLoadState = UiLoadState.Success, groupRoom = groupRoom) }
                },
                onFailure = {
                    setState { copy(groupRoomLoadState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun getGroupComment() {
        viewModelScope.launch {
            setState { copy(commentState = UiLoadState.Loading) }
            getGroupCommentsUseCase(
                isPublic = false,
                groupId = currentState.groupRoom.groupInfo.groupId,
                groupType = currentState.groupRoom.groupInfo.cycle
            ).fold(
                onSuccess = { groupComments ->
                    setState { copy(commentState = UiLoadState.Success) }
                    updateGroupRoom { copy(groupComments = groupComments) }
                },
                onFailure = {
                    setState { copy(commentState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun reportComment(commentId: Int) {
        viewModelScope.launch {
            reportCommentUseCase(commentId).fold(
                onSuccess = {
                    setState { copy(commentReportState = UiLoadState.Success) }
                },
                onFailure = {
                    setState { copy(commentReportState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun postInputComment() {
        viewModelScope.launch {
            setState { copy(commentState = UiLoadState.Loading) }
            postCommentUseCase(
                comment = Comment(
                    groupId = currentState.groupRoom.groupInfo.groupId,
                    cycle = currentState.groupRoom.groupInfo.cycle,
                    isPublic = false,
                    content = currentState.inputComment
                )
            ).fold(
                onSuccess = { groupComments ->
                    setState { copy(commentState = UiLoadState.Success, inputComment = "") }
                    updateGroupRoom { copy(groupComments = groupComments) }
                },
                onFailure = {
                    setState { copy(commentState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            setState { copy(commentState = UiLoadState.Loading) }
            deleteCommentUseCase(commentId).fold(
                onSuccess = {
                    setState { copy(commentState = UiLoadState.Success) }
                    setEvent(GroupRoomContract.Event.OnCommentRefreshClick)
                },
                onFailure = {
                    setState { copy(commentState = UiLoadState.Error) }
                }
            )
        }
    }
}
