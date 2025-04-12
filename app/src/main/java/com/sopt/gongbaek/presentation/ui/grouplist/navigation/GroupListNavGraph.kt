package com.sopt.gongbaek.presentation.ui.grouplist.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.sopt.gongbaek.presentation.ui.grouplist.screen.GroupListRoute
import com.sopt.gongbaek.presentation.ui.groupregister.navigation.navigateGroupRegisterNavGraph

fun NavGraphBuilder.groupListNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    composable<MainBottomTabRoute.GroupList> {
        GroupListRoute(
            navigateGroupDetail = { groupId, groupCycle ->
                navController.navigateGroupDetail(groupId, groupCycle)
            },
            navigateGroupRegister = navController::navigateGroupRegisterNavGraph,
            innerPadding = innerPadding
        )
    }
}
