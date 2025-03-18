package com.sopt.gongbaek.presentation.ui.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.sopt.gongbaek.presentation.model.NavigationRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(
        route = NavigationRoute.MainBottomNavBarTabRoute.MY_PAGE_TAB,
        navOptions = navOptions
    )
}
