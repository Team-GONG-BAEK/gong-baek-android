package com.sopt.gongbaek.presentation.ui.groupdetail.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.NavigationRoute

fun NavController.navigateGroupDetailNavGraph() {
    navigate(route = NavigationRoute.GROUP_DETAIL)
}

fun NavController.navigateGroupDetail(groupId: Int, groupCycle: String) {
    navigate(
        route = NavigationRoute.GROUP_DETAIL
            .replace("{groupId}", groupId.toString())
            .replace("{groupCycle}", groupCycle)
    )
}
