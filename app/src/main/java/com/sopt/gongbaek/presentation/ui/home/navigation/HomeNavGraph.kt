package com.sopt.gongbaek.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.navigateGroupList
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.navigateGroupRoom
import com.sopt.gongbaek.presentation.ui.home.screen.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    composable<MainBottomTabRoute.Home> {
        HomeRoute(
            navigateGroupDetail = { groupId, groupCycle ->
                navController.navigateGroupDetail(groupId, groupCycle)
            },
            navigateGroupRoom = { groupId, groupType ->
                navController.navigateGroupRoom(groupId, groupType)
            },
            navigateGroupList = navController::navigateGroupList,
            innerPadding = innerPadding
        )
    }
}
