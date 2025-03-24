package com.sopt.gongbaek.presentation.ui.grouplist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute

fun NavController.navigateGroupListNavGraph(navOptions: NavOptions) {
    navigate(
        route = MainBottomTabRoute.GroupList,
        navOptions = navOptions
    )
}

fun NavController.navigateGroupList() {
    navigate(MainBottomTabRoute.GroupList)
}
