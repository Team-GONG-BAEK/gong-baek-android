package com.gongbaek.android.presentation.ui.grouproom.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.ui.grouproom.screen.GroupRoomRoute

fun NavGraphBuilder.groupRoomNavGraph(
    navController: NavHostController,
    navigateBack: () -> Unit
) {
    composable<NavigationRoute.GroupRoom> {
        GroupRoomRoute(
            navigateBack = navigateBack
        )
    }
}
