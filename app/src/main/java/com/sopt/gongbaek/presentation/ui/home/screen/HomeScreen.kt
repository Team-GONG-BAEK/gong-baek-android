package com.sopt.gongbaek.presentation.ui.home.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.presentation.ui.component.stateView.ErrorScreen
import com.sopt.gongbaek.presentation.ui.component.stateView.LoadingScreen
import com.sopt.gongbaek.presentation.ui.home.component.section.HomeBannerSection
import com.sopt.gongbaek.presentation.ui.home.component.section.MemberRecommendSection
import com.sopt.gongbaek.presentation.ui.home.component.section.NearestGroupSection
import com.sopt.gongbaek.presentation.ui.home.component.section.OnceRecommendSection
import com.sopt.gongbaek.presentation.ui.home.component.section.WeekRecommendSection
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme

@Composable
fun HomeRoute(
    navigateGroupDetail: (Int, String) -> Unit,
    navigateGroupRoom: (Int, String) -> Unit,
    navigateGroupList: () -> Unit,
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is HomeContract.SideEffect.NavigateToGroupDetail -> navigateGroupDetail(sideEffect.groupId, sideEffect.groupType)
                    is HomeContract.SideEffect.NavigateToGroupRoom -> navigateGroupRoom(sideEffect.groupId, sideEffect.groupType)
                    is HomeContract.SideEffect.NavigateToGroupList -> navigateGroupList()
                }
            }
    }

    when (uiState.homeLoadState) {
        UiLoadState.Idle -> LoadingScreen()
        UiLoadState.Loading -> LoadingScreen()
        UiLoadState.Error -> ErrorScreen(
            onClickRetry = { viewModel.setEvent(HomeContract.Event.OnFetchHomeData) }
        )
        UiLoadState.Success ->
            HomeScreen(
                uiState = uiState,
                onClickWeekRecommendItem = { groupId, groupCycle ->
                    viewModel.sendSideEffect(HomeContract.SideEffect.NavigateToGroupDetail(groupId, groupCycle))
                },
                onClickOnceRecommendItem = { groupId, groupCycle ->
                    viewModel.sendSideEffect(HomeContract.SideEffect.NavigateToGroupDetail(groupId, groupCycle))
                },
                onFillGroupClick = {
                    viewModel.sendSideEffect(HomeContract.SideEffect.NavigateToGroupList)
                },
                onNearestGroupClick = { groupId, groupType ->
                    viewModel.sendSideEffect(HomeContract.SideEffect.NavigateToGroupRoom(groupId, groupType))
                },
                modifier = Modifier.padding(innerPadding)
            )
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    onClickWeekRecommendItem: (Int, String) -> Unit,
    onClickOnceRecommendItem: (Int, String) -> Unit,
    onFillGroupClick: () -> Unit,
    onNearestGroupClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            NearestGroupSection(
                university = uiState.userProfile.schoolName,
                nearestGroup = uiState.nearestGroup,
                onNearestGroupClick = onNearestGroupClick,
                onFillGroupClick = onFillGroupClick,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }

        item {
            WeekRecommendSection(
                userNickname = uiState.userProfile.nickname,
                weekRecommendGroupInfo = uiState.weekRecommendGroupList,
                onClickWeekRecommendItem = onClickWeekRecommendItem,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }

        item {
            OnceRecommendSection(
                onceRecommendGroupInfo = uiState.onceRecommendGroupList,
                onClickOnceRecommendItem = onClickOnceRecommendItem,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }

        item {
            HomeBannerSection(
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }

        item {
            MemberRecommendSection(
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    GONGBAEKTheme {
        HomeScreen(
            uiState = HomeContract.State(),
            onClickWeekRecommendItem = { _, _ -> },
            onClickOnceRecommendItem = { _, _ -> },
            onNearestGroupClick = { _, _ -> },
            onFillGroupClick = {}
        )
    }
}
