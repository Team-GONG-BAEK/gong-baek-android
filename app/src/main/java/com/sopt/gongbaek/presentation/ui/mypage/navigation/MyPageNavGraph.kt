package com.sopt.gongbaek.presentation.ui.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.mypage.screen.MyPageRoute

fun NavGraphBuilder.myPageNavGraph(
    navController: NavController
) {
    composable(
        route = NavigationRoute.MainBottomNavBarTabRoute.MY_PAGE_TAB,
    ) {
        MyPageRoute()
    }
}
