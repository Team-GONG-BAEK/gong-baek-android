package com.gongbaek.android.presentation.ui.groupdetail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.MainBottomTabRoute
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.ui.groupdetail.screen.GroupDetailRoute
import com.gongbaek.android.presentation.ui.grouproom.navigation.navigateGroupRoom

fun NavGraphBuilder.groupDetailNavGraph(
    navController: NavHostController,
    navigateBack: () -> Unit
) {
    composable<NavigationRoute.GroupDetail> {
        GroupDetailRoute(
            navigateBack = navigateBack,
            navigateGroupRoom = { groupId, groupCycle ->
                navController.navigateGroupRoom(groupId, groupCycle)
            },
            navigateMyPage = { navController.navigate(MainBottomTabRoute.MyPage) }
        )
    }
}
