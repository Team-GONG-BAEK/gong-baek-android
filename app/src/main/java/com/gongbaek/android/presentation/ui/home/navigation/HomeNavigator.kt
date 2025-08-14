package com.gongbaek.android.presentation.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.gongbaek.android.presentation.model.MainBottomTabRoute

fun NavController.navigateHomeNavGraph(navOptions: NavOptions) {
    navigate(
        route = MainBottomTabRoute.Home,
        navOptions = navOptions
    )
}
