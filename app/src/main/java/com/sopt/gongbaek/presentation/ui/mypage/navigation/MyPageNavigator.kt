package com.sopt.gongbaek.presentation.ui.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(
        route = MainBottomTabRoute.MyPage,
        navOptions = navOptions
    )
}
