package com.sopt.gongbaek.presentation.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute

fun NavController.navigateHomeNavGraph(navOptions: NavOptions) {
    navigate(
        route = MainBottomTabRoute.Home,
        navOptions = navOptions
    )
}
