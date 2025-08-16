package com.gongbaek.android.presentation.ui.grouplist.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.MainBottomTabRoute
import com.gongbaek.android.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.gongbaek.android.presentation.ui.grouplist.screen.GroupListRoute
import com.gongbaek.android.presentation.ui.groupregister.navigation.navigateGroupRegisterNavGraph

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
