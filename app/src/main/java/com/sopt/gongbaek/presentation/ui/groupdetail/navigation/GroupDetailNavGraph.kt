package com.sopt.gongbaek.presentation.ui.groupdetail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.screen.GroupDetailRoute
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.navigateGroupRoom

fun NavGraphBuilder.groupDetailNavGraph(
    navController: NavHostController
) {
    composable(
        route = NavigationRoute.GROUP_DETAIL,
        arguments = listOf(
            navArgument("groupId") { type = NavType.IntType },
            navArgument("groupCycle") { type = NavType.StringType }
        )
    ) {
        GroupDetailRoute(
            navigateBack = navController::popBackStack,
            navigateGroupRoom = { groupId, groupCycle ->
                navController.navigateGroupRoom(groupId, groupCycle)
            }
        )
    }
}
