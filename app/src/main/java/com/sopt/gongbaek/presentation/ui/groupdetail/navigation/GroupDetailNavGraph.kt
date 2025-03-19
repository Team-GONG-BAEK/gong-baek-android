package com.sopt.gongbaek.presentation.ui.groupdetail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.screen.GroupDetailRoute
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.navigateGroupRoom

fun NavGraphBuilder.groupDetailNavGraph(
    navController: NavHostController
) {
    composable<NavigationRoute.GroupDetail> {
        GroupDetailRoute(
            navigateBack = navController::popBackStack,
            navigateGroupRoom = { groupId, groupCycle ->
                navController.navigateGroupRoom(groupId, groupCycle)
            }
        )
    }
}
