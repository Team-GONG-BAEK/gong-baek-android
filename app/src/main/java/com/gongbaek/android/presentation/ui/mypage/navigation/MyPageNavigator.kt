package com.gongbaek.android.presentation.ui.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.gongbaek.android.presentation.model.MainBottomTabRoute
import com.gongbaek.android.presentation.model.NavigationRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(
        route = MainBottomTabRoute.MyPage,
        navOptions = navOptions
    )
}

fun NavController.navigateSetting() = navigate(NavigationRoute.Setting)
