package com.sopt.gongbaek.presentation.ui.grouproom.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.grouproom.screen.GroupRoomRoute

fun NavGraphBuilder.groupRoomNavGraph(
    navController: NavHostController
) {
    composable<NavigationRoute.GroupRoom> {
        GroupRoomRoute(
            navigateBack = navController::popBackStack
        )
    }
}
