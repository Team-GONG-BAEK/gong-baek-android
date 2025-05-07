package com.sopt.gongbaek.presentation.ui.groupdetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.domain.model.GroupComments
import com.sopt.gongbaek.domain.model.GroupDetail
import com.sopt.gongbaek.domain.model.GroupHost
import com.sopt.gongbaek.domain.model.GroupInfo
import com.sopt.gongbaek.presentation.type.GongBaekDialogType
import com.sopt.gongbaek.presentation.type.GroupDetailPagerType
import com.sopt.gongbaek.presentation.type.GroupInfoChipType
import com.sopt.gongbaek.presentation.ui.component.dialog.GongBaekDialog
import com.sopt.gongbaek.presentation.ui.component.section.CommentSection
import com.sopt.gongbaek.presentation.ui.component.section.GroupInfoSection
import com.sopt.gongbaek.presentation.ui.component.tabpager.CustomTabPager
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.formatGroupTimeDescription
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun GroupDetailRoute(
    viewModel: GroupDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateGroupRoom: (Int, String) -> Unit
) {
    val groupDetailUiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val groupDetailTabs: List<String> = GroupDetailPagerType.entries.map { it.description }
    val pagerState = rememberPagerState { groupDetailTabs.size }

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> viewModel.setEvent(GroupDetailContract.Event.OnGroupInfoTabClick)
            1 -> viewModel.setEvent(GroupDetailContract.Event.OnCommentTabClick)
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is GroupDetailContract.SideEffect.NavigateBack -> navigateBack()
                    is GroupDetailContract.SideEffect.NavigateGroupRoom -> {
                        navigateGroupRoom(sideEffect.groupId, sideEffect.groupCycle)
                    }
                }
            }
    }

    GroupDetailScreen(
        uiState = groupDetailUiState,
        groupDetailTabs = groupDetailTabs,
        pagerState = pagerState,
        onBackClick = { viewModel.sendSideEffect(GroupDetailContract.SideEffect.NavigateBack) },
        onApplyClick = { viewModel.setEvent(GroupDetailContract.Event.OnApplyClick) },
        onDialogConfirmClick = { groupId, groupCycle -> viewModel.sendSideEffect(GroupDetailContract.SideEffect.NavigateGroupRoom(groupId, groupCycle)) },
        onDialogDismissClick = { viewModel.setEvent(GroupDetailContract.Event.OnDialogDismissClick) },
        updateInputComment = { inputComment -> viewModel.setEvent(GroupDetailContract.Event.UpdateInputComment(inputComment)) },
        onCommentRefreshClick = { viewModel.setEvent(GroupDetailContract.Event.OnCommentRefreshClick) },
        onCommentPostClick = { viewModel.setEvent(GroupDetailContract.Event.OnCommentPostClick) },
        onCommentDeleteClick = { commentId -> viewModel.setEvent(GroupDetailContract.Event.OnCommentDeleteClick(commentId)) }
    )
}

@Composable
fun GroupDetailScreen(
    uiState: GroupDetailContract.State,
    groupDetailTabs: List<String>,
    pagerState: PagerState,
    onBackClick: () -> Unit,
    onApplyClick: () -> Unit,
    onDialogConfirmClick: (Int, String) -> Unit,
    onDialogDismissClick: () -> Unit,
    updateInputComment: (String) -> Unit,
    onCommentRefreshClick: () -> Unit,
    onCommentPostClick: () -> Unit,
    onCommentDeleteClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StartTitleTopBar(
            modifier = Modifier.background(color = GongBaekTheme.colors.white),
            startTitleResId = R.string.topbar_group,
            onLeadingIconClick = onBackClick,
            isTrailingIconIncluded = true
        )
        GroupInfoSection(
            groupStatus = GroupInfoChipType.getChipTypeFromStatus(uiState.groupDetail.groupInfo.status),
            groupCategory = GroupInfoChipType.getChipTypeFromCategory(uiState.groupDetail.groupInfo.category),
            groupCycle = GroupInfoChipType.getChipTypeFromCycle(uiState.groupDetail.groupInfo.cycle),
            groupTitle = uiState.groupDetail.groupInfo.title,
            groupTime = formatGroupTimeDescription(uiState.groupDetail.groupInfo),
            groupPlace = uiState.groupDetail.groupInfo.place,
            groupCover = uiState.groupDetail.groupInfo.coverImg,
            modifier = Modifier
                .background(color = GongBaekTheme.colors.white)
                .padding(16.dp)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 8.dp,
            color = GongBaekTheme.colors.gray02
        )
        CustomTabPager(
            tabs = groupDetailTabs,
            pagerState = pagerState
        )
        HorizontalPager(
            state = pagerState,
            pageContent = { page ->
                when (page) {
                    0 -> GroupDetailInfoSection(
                        groupInfo = uiState.groupDetail.groupInfo,
                        groupHost = uiState.groupDetail.groupHost,
                        onApplyClick = onApplyClick
                    )

                    1 -> CommentSection(
                        groupComments = uiState.groupDetail.groupComments,
                        value = uiState.inputComment,
                        onValueChanged = updateInputComment,
                        onRefreshClicked = onCommentRefreshClick,
                        onDeleteClicked = onCommentDeleteClick,
                        onSendClicked = onCommentPostClick
                    )
                }
            }
        )
    }

    if (uiState.groupApplyState == UiLoadState.Success) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            Dialog(
                onDismissRequest = onDialogDismissClick
            ) {
                GongBaekDialog(
                    gongBaekDialogType = GongBaekDialogType.ENTER_SUCCESS,
                    onConfirmButtonClick = { onDialogConfirmClick(uiState.groupDetail.groupInfo.groupId, uiState.groupDetail.groupInfo.cycle) },
                    onDismissButtonClick = onDialogDismissClick
                )
            }
        }
    }

    if (uiState.groupApplyState == UiLoadState.Error) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            Dialog(
                onDismissRequest = onDialogDismissClick
            ) {
                GongBaekDialog(
                    gongBaekDialogType = GongBaekDialogType.ENTER_FAIL,
                    onConfirmButtonClick = onDialogDismissClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupDetailScreenPreview() {
    GONGBAEKTheme {
        GroupDetailScreen(
            uiState = GroupDetailContract.State(
                groupDetail = GroupDetail(
                    groupInfo = GroupInfo(
                        groupId = 1,
                        coverImg = 0,
                        status = "RECRUITING",
                        category = "STUDY",
                        cycle = "WEEKLY",
                        title = "컴포즈 공부 모임",
                        date = "2025-04-20",
                        dayOfWeek = "MON",
                        startTime = 14.0,
                        endTime = 16.0,
                        place = "SOPT 강의실",
                        introduction = "Jetpack Compose를 함께 공부해요!",
                        maxPeopleCount = 10,
                        currentPeopleCount = 5,
                        isHost = false,
                        isApply = false
                    ),
                    groupHost = GroupHost(
                        profileImg = 0,
                        nickname = "밍서",
                        gender = "WOMAN",
                        major = "컴퓨터공학과",
                        enterYear = 2021,
                        mbti = "INFP",
                        introduction = "안녕하세요~ 함께 재밌게 활동해요!"
                    ),
                    groupComments = GroupComments(
                        groupId = 1,
                        groupStatus = "RECRUITING",
                        groupCycle = "WEEKLY",
                        commentCount = 2,
                        groupCommentList = listOf(
                            GroupComments.GroupComment(
                                commentId = 1,
                                commentWriter = "홍길동",
                                commentContent = "정말 기대돼요!",
                                createdAt = "2025-04-19-10-30",
                                isGroupHost = false,
                                isWriter = false
                            ),
                            GroupComments.GroupComment(
                                commentId = 2,
                                commentWriter = "김솝트",
                                commentContent = "저도 참여하고 싶어요!",
                                createdAt = "2025-04-19-11-45",
                                isGroupHost = true,
                                isWriter = true
                            )
                        )
                    )
                ),
                inputComment = "이 스터디 너무 좋아 보여요!",
                groupApplyState = UiLoadState.Idle,
                commentState = UiLoadState.Success
            ),
            groupDetailTabs = GroupDetailPagerType.entries.map { it.description },
            pagerState = rememberPagerState { GroupDetailPagerType.entries.size },
            onBackClick = {},
            onApplyClick = {},
            onDialogConfirmClick = { _, _ -> },
            onDialogDismissClick = {},
            updateInputComment = {},
            onCommentRefreshClick = {},
            onCommentPostClick = {},
            onCommentDeleteClick = {}
        )
    }
}
