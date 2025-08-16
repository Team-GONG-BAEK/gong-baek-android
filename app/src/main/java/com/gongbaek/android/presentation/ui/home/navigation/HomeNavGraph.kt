package com.gongbaek.android.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.MainBottomTabRoute
import com.gongbaek.android.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.gongbaek.android.presentation.ui.grouplist.navigation.navigateGroupList
import com.gongbaek.android.presentation.ui.grouproom.navigation.navigateGroupRoom
import com.gongbaek.android.presentation.ui.home.screen.HomeRoute

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
