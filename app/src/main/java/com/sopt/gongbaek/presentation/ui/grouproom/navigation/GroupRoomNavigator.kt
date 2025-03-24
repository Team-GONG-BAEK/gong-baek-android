package com.sopt.gongbaek.presentation.ui.grouproom.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.NavigationRoute

fun NavController.navigateGroupRoom(groupId: Int, groupCycle: String) {
    navigate(
        NavigationRoute.GroupRoom(
            groupId = groupId,
            groupCycle = groupCycle
        )
    )
}
