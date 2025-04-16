package com.sopt.gongbaek.presentation.ui.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.model.ProfileImageList
import com.sopt.gongbaek.presentation.type.MyGroupPagerType
import com.sopt.gongbaek.presentation.ui.component.tabpager.CustomTabPager
import com.sopt.gongbaek.presentation.ui.component.topbar.CenterTitleTopBar
import com.sopt.gongbaek.presentation.ui.mypage.component.MyGroupScreenContent
import com.sopt.gongbaek.presentation.util.extension.roundedBackgroundWithBorder
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateSetting: () -> Unit,
    navigateGroupDetail: (Int, String) -> Unit,
    navigateGroupRoom: (Int, String) -> Unit,
    innerPadding: PaddingValues
) {
    val myPageUiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val myPageTabs: List<String> = MyGroupPagerType.entries.map { it.description }
    val pagerState = rememberPagerState { myPageTabs.size }

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> viewModel.setEvent(MyPageContract.Event.OnRegisterGroupsTabClick)
            1 -> viewModel.setEvent(MyPageContract.Event.OnApplyGroupsTabClick)
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyPageContract.SideEffect.NavigateSetting -> navigateSetting()
                    is MyPageContract.SideEffect.NavigateGroupDetail -> {
                        navigateGroupDetail(sideEffect.groupId, sideEffect.groupCycle)
                    }

                    is MyPageContract.SideEffect.NavigateGroupRoom -> {
                        navigateGroupRoom(sideEffect.groupId, sideEffect.groupCycle)
                    }

                    else -> {}
                }
            }
    }

    MyPageScreen(
        uiState = myPageUiState,
        onSettingButtonClicked = {
            viewModel.sendSideEffect(MyPageContract.SideEffect.NavigateSetting)
        },
        myPageTabs = myPageTabs,
        pagerState = pagerState,
        onGroupDetailButtonClick = { groupId, groupCycle ->
            viewModel.sendSideEffect(MyPageContract.SideEffect.NavigateGroupDetail(groupId, groupCycle))
        },
        onGroupRoomButtonClick = { groupId, groupCycle ->
            viewModel.sendSideEffect(MyPageContract.SideEffect.NavigateGroupRoom(groupId, groupCycle))
        },
        modifier = Modifier.padding(innerPadding)
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageContract.State,
    onSettingButtonClicked: () -> Unit,
    myPageTabs: List<String>,
    pagerState: PagerState,
    onGroupDetailButtonClick: (Int, String) -> Unit,
    onGroupRoomButtonClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CenterTitleTopBar(
            centerTitleResId = R.string.topbar_my_page,
            trailingIconResId = R.drawable.ic_setting_48,
            onTrailingIconClick = onSettingButtonClicked,
            trailingIconColor = GongBaekTheme.colors.gray08
        )

        MyInfoSection(uiState = uiState)

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 8.dp,
            color = GongBaekTheme.colors.gray02
        )

        MyGroupSection(
            uiState = uiState,
            myGroupTabs = myPageTabs,
            pagerState = pagerState,
            onGroupDetailButtonClick = onGroupDetailButtonClick,
            onGroupRoomButtonClick = onGroupRoomButtonClick
        )
    }
}

@Composable
private fun MyInfoSection(
    uiState: MyPageContract.State
) {
    val imageList = ProfileImageList.profileImageList
    val profileImageResId = if (imageList.isNotEmpty() && uiState.myPageInfo.profileImage in 1..imageList.size) {
        imageList[uiState.myPageInfo.profileImage - 1]
    } else {
        R.drawable.img_detail_profile_0
    }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(profileImageResId),
            contentDescription = null,
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.22).dp)
                .aspectRatio(1f / 1f)
                .clip(shape = RoundedCornerShape(2.dp))
                .roundedBackgroundWithBorder(
                    cornerRadius = 2.dp,
                    backgroundColor = Color.Transparent,
                    borderColor = GongBaekTheme.colors.gray02,
                    borderWidth = 1.dp
                )
        )
        Spacer(Modifier.width(14.dp))

        Column {
            Text(
                text = uiState.myPageInfo.nickname,
                color = GongBaekTheme.colors.black,
                style = GongBaekTheme.typography.title1.m20,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.mypage_school_major_introduction, uiState.myPageInfo.school, uiState.myPageInfo.major),
                color = GongBaekTheme.colors.gray08,
                style = GongBaekTheme.typography.body2.r14
            )
        }
    }
}

@Composable
private fun MyGroupSection(
    uiState: MyPageContract.State,
    myGroupTabs: List<String>,
    pagerState: PagerState,
    onGroupDetailButtonClick: (Int, String) -> Unit,
    onGroupRoomButtonClick: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomTabPager(
            tabs = myGroupTabs,
            pagerState = pagerState
        )
        HorizontalPager(
            state = pagerState,
            pageContent = { page ->
                when (page) {
                    0 -> {
                        MyGroupScreenContent(
                            activeGroups = uiState.registerActiveGroups,
                            closedGroups = uiState.registerClosedGroups,
                            onGroupDetailButtonClick = onGroupDetailButtonClick,
                            onGroupRoomButtonClick = onGroupRoomButtonClick
                        )
                    }

                    1 -> {
                        MyGroupScreenContent(
                            activeGroups = uiState.applyActiveGroups,
                            closedGroups = uiState.applyClosedGroups,
                            onGroupDetailButtonClick = onGroupDetailButtonClick,
                            onGroupRoomButtonClick = onGroupRoomButtonClick
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    Column {
        MyPageScreen(
            uiState = MyPageContract.State(),
            onSettingButtonClicked = {},
            myPageTabs = MyGroupPagerType.entries.map { it.description },
            pagerState = rememberPagerState { MyGroupPagerType.entries.map { it.description }.size },
            onGroupDetailButtonClick = { _, _ -> },
            onGroupRoomButtonClick = { _, _ -> }
        )
    }
}
