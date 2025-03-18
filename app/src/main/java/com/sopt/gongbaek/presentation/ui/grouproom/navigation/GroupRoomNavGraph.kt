package com.sopt.gongbaek.presentation.ui.grouproom.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.grouproom.screen.GroupRoomRoute

fun NavGraphBuilder.groupRoomNavGraph(
    navController: NavHostController
) {
    composable(
        route = NavigationRoute.GROUP_ROOM,
        arguments = listOf(
            navArgument("groupId") { type = NavType.IntType },
            navArgument("groupCycle") { type = NavType.StringType }
        )
    ) {
        GroupRoomRoute(
            // TODO 마이 페이지 작업 시 기획의도에 맞게 반영예정
            navigateMyGroup = { }
        )
    }
}
