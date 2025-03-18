package com.sopt.gongbaek.presentation.ui.grouplist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.sopt.gongbaek.presentation.ui.grouplist.screen.GroupListRoute
import com.sopt.gongbaek.presentation.ui.groupregister.navigation.navigateGroupRegisterNavGraph

fun NavGraphBuilder.groupListNavGraph(
    navController: NavHostController
) {
    composable(
        route = NavigationRoute.MainBottomNavBarTabRoute.GROUP_LIST_TAB
    ) {
        GroupListRoute(
            navigateGroupDetail = { groupId, groupCycle ->
                navController.navigateGroupDetail(groupId, groupCycle)
            },
            navigateGroupRegister = navController::navigateGroupRegisterNavGraph
        )
    }
}
