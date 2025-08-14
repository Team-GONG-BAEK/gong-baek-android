package com.gongbaek.android.presentation.ui.grouplist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.gongbaek.android.presentation.model.MainBottomTabRoute

fun NavController.navigateGroupListNavGraph(navOptions: NavOptions) {
    navigate(
        route = MainBottomTabRoute.GroupList,
        navOptions = navOptions
    )
}

fun NavController.navigateGroupList() {
    navigate(MainBottomTabRoute.GroupList)
}
