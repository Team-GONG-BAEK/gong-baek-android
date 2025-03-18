package com.sopt.gongbaek.presentation.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.navigateGroupList
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.navigateGroupRoom
import com.sopt.gongbaek.presentation.ui.home.screen.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    composable(
        route = NavigationRoute.MainBottomNavBarTabRoute.HOME_TAB
    ) {
        HomeRoute(
            navigateGroupDetail = { groupId, groupCycle ->
                navController.navigateGroupDetail(groupId, groupCycle)
            },
            navigateGroupRoom = { groupId, groupType ->
                navController.navigateGroupRoom(groupId, groupType)
            },
            navigateGroupList = navController::navigateGroupList
        )
    }
}
