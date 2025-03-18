package com.sopt.gongbaek.presentation.ui.grouproom.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.NavigationRoute

fun NavController.navigateGroupRoomNavGraph() {
    navigate(route = NavigationRoute.GROUP_ROOM)
}

fun NavController.navigateGroupRoom(groupId: Int, groupCycle: String) {
    navigate(
        route = NavigationRoute.GROUP_ROOM
            .replace("{groupId}", groupId.toString())
            .replace("{groupCycle}", groupCycle)
    )
}
