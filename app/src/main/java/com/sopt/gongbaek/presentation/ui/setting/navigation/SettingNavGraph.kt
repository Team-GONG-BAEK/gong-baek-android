package com.sopt.gongbaek.presentation.ui.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.setting.screen.SettingRoute

fun NavGraphBuilder.settingNavGraph(
    navController: NavHostController
) {
    composable<NavigationRoute.Setting> {
        SettingRoute()
    }
}
