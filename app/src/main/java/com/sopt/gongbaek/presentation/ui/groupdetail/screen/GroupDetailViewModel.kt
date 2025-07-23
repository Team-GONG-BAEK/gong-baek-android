package com.sopt.gongbaek.presentation.ui.groupdetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sopt.gongbaek.data.remote.util.HttpResponseException
import com.sopt.gongbaek.domain.model.Comment
import com.sopt.gongbaek.domain.model.GroupDetail
import com.sopt.gongbaek.domain.usecase.ApplyGroupUseCase
import com.sopt.gongbaek.domain.usecase.CancelGroupUseCase
import com.sopt.gongbaek.domain.usecase.DeleteCommentUseCase
import com.sopt.gongbaek.domain.usecase.DeleteGroupUseCase
import com.sopt.gongbaek.domain.usecase.GetGroupCommentsUseCase
import com.sopt.gongbaek.domain.usecase.LoadGroupDetailScreenUseCase
import com.sopt.gongbaek.domain.usecase.PostCommentUseCase
import com.sopt.gongbaek.domain.usecase.ReportCommentUseCase
import com.sopt.gongbaek.domain.usecase.ReportGroupUseCase
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadGroupDetailScreenUseCase: LoadGroupDetailScreenUseCase,
    private val applyGroupUseCase: ApplyGroupUseCase,
    private val cancelGroupUseCase: CancelGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val reportGroupUseCase: ReportGroupUseCase,
    private val getGroupCommentsUseCase: GetGroupCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase
) : BaseViewModel<GroupDetailContract.State, GroupDetailContract.Event, GroupDetailContract.SideEffect>() {

    init {
        val groupId: Int = savedStateHandle.toRoute<NavigationRoute.GroupDetail>().groupId
        val groupCycle: String = savedStateHandle.toRoute<NavigationRoute.GroupDetail>().groupCycle
        updateGroupDetail {
            copy(
                groupInfo = groupInfo.copy(
                    groupId = groupId,
                    cycle = groupCycle
                )
            )
        }
    }

    override fun createInitialState(): GroupDetailContract.State = GroupDetailContract.State()

    override suspend fun handleEvent(event: GroupDetailContract.Event) {
        when (event) {
            is GroupDetailContract.Event.OnGroupReportClick -> {
                setState { copy(showGroupReportDialog = true) }
            }
            is GroupDetailContract.Event.DismissGroupReport -> {
                setState { copy(showGroupReportDialog = false) }
            }
            is GroupDetailContract.Event.ConfirmGroupReport -> {
                setState { copy(showGroupReportDialog = false) }
                reportGroup(event.groupId, event.groupType)
            }
            is GroupDetailContract.Event.ResetGroupReportState -> {
                setState { copy(groupReportState = UiLoadState.Idle) }
            }
            is GroupDetailContract.Event.OnGroupInfoTabClick -> {
                getGroupDetailInfo()
            }
            is GroupDetailContract.Event.OnApplyClick -> {
                applyGroup()
            }
            is GroupDetailContract.Event.ResetApplyState -> {
                setState { copy(groupApplyState = UiLoadState.Idle) }
            }
            is GroupDetailContract.Event.OnCancelClick -> {
                cancelGroup()
            }
            is GroupDetailContract.Event.ResetCancelState -> {
                setState { copy(groupCancelState = UiLoadState.Idle) }
            }
            is GroupDetailContract.Event.ShowDeleteDialog -> {
                setState { copy(groupDeleteState = UiLoadState.Loading) }
            }
            is GroupDetailContract.Event.OnDeleteClick -> {
                deleteGroup()
            }
            is GroupDetailContract.Event.ResetDeleteState -> {
                setState { copy(groupDeleteState = UiLoadState.Idle) }
            }
            is GroupDetailContract.Event.OnCommentTabClick -> {
                getGroupDetailInfo()
            }
            is GroupDetailContract.Event.UpdateInputComment -> setState {
                copy(inputComment = event.inputComment)
            }
            is GroupDetailContract.Event.OnCommentRefreshClick -> {
                getGroupComment()
            }
            is GroupDetailContract.Event.OnCommentReportClick -> {
                setState { copy(showCommentReportDialog = true) }
            }
            is GroupDetailContract.Event.DismissCommentReport -> {
                setState { copy(showCommentReportDialog = false) }
            }
            is GroupDetailContract.Event.ConfirmCommentReport -> {
                setState { copy(showCommentReportDialog = false) }
                reportComment(event.commentId)
            }
            is GroupDetailContract.Event.ResetCommentReportState -> {
                setState { copy(commentReportState = UiLoadState.Idle) }
            }
            is GroupDetailContract.Event.OnCommentPostClick -> {
                postInputComment()
            }
            is GroupDetailContract.Event.OnCommentDeleteClick -> {
                deleteComment(event.commentId)
            }
        }
    }

    fun sendSideEffect(sideEffect: GroupDetailContract.SideEffect) = setSideEffect(sideEffect)

    private fun updateGroupDetail(update: GroupDetail.() -> GroupDetail) =
        setState { copy(groupDetail = groupDetail.update()) }

    private fun getGroupDetailInfo() {
        viewModelScope.launch {
            setState { copy(groupDetailLoadState = UiLoadState.Loading) }
            loadGroupDetailScreenUseCase(
                groupId = currentState.groupDetail.groupInfo.groupId,
                groupType = currentState.groupDetail.groupInfo.cycle
            ).fold(
                onSuccess = { groupDetail ->
                    setState { copy(groupDetailLoadState = UiLoadState.Success, groupDetail = groupDetail) }
                },
                onFailure = {
                    setState { copy(groupDetailLoadState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun applyGroup() {
        viewModelScope.launch {
            setState { copy(groupApplyState = UiLoadState.Loading) }
            applyGroupUseCase(
                groupId = currentState.groupDetail.groupInfo.groupId,
                groupType = currentState.groupDetail.groupInfo.cycle
            ).fold(
                onSuccess = { setState { copy(groupApplyState = UiLoadState.Success) } },
                onFailure = { t ->
                    if (t is HttpResponseException) {
                        when (t.code) {
                            GROUP_ALREADY_FULL -> {
                                setState { copy(groupApplyStatusCode = GROUP_ALREADY_FULL) }
                                setState { copy(groupApplyState = UiLoadState.Error) }
                            }
                            else -> {
                                setState { copy(groupApplyState = UiLoadState.Error) }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun cancelGroup() {
        viewModelScope.launch {
            setState { copy(groupCancelState = UiLoadState.Loading) }
            cancelGroupUseCase(
                groupId = currentState.groupDetail.groupInfo.groupId,
                groupType = currentState.groupDetail.groupInfo.cycle
            ).fold(
                onSuccess = { setState { copy(groupCancelState = UiLoadState.Success) } },
                onFailure = { setState { copy(groupCancelState = UiLoadState.Error) } }
            )
        }
    }

    private fun deleteGroup() {
        viewModelScope.launch {
            deleteGroupUseCase(
                groupId = currentState.groupDetail.groupInfo.groupId,
                groupType = currentState.groupDetail.groupInfo.cycle
            ).fold(
                onSuccess = { setState { copy(groupDeleteState = UiLoadState.Success) } },
                onFailure = { setState { copy(groupDeleteState = UiLoadState.Error) } }
            )
        }
    }

    private fun reportGroup(groupId: Int, groupType: String) {
        viewModelScope.launch {
            reportGroupUseCase(groupId, groupType).fold(
                onSuccess = {
                    setState { copy(groupReportState = UiLoadState.Success) }
                },
                onFailure = {
                    setState { copy(groupReportState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun getGroupComment() {
        viewModelScope.launch {
            setState { copy(commentState = UiLoadState.Loading) }
            getGroupCommentsUseCase(
                isPublic = true,
                groupId = currentState.groupDetail.groupInfo.groupId,
                groupType = currentState.groupDetail.groupInfo.cycle
            ).fold(
                onSuccess = { groupComments ->
                    setState { copy(commentState = UiLoadState.Success) }
                    updateGroupDetail { copy(groupComments = groupComments) }
                },
                onFailure = {
                    setState { copy(commentState = UiLoadState.Error) }
                }
            )
        }
    }

    private fun postInputComment() {
        viewModelScope.launch {
            setState { copy(commentState = UiLoadState.Loading) }
            postCommentUseCase(
                comment = Comment(
                    groupId = currentState.groupDetail.groupInfo.groupId,
                    cycle = currentState.groupDetail.groupInfo.cycle,
                    isPublic = true,
                    content = currentState.inputComment
                )
            ).fold(
                onSuccess = { groupComments ->
                    setState { copy(commentState = UiLoadState.Success, inputComment = "") }
                    updateGroupDetail { copy(groupComments = groupComments) }
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
                    setEvent(GroupDetailContract.Event.OnCommentRefreshClick)
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

    companion object {
        const val GROUP_ALREADY_FULL = 4097
    }
}
