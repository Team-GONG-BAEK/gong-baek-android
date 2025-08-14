package com.gongbaek.android.presentation.ui.grouproom.navigation

import androidx.navigation.NavController
import com.gongbaek.android.presentation.model.NavigationRoute

fun NavController.navigateGroupRoom(groupId: Int, groupCycle: String) {
    navigate(
        NavigationRoute.GroupRoom(
            groupId = groupId,
            groupCycle = groupCycle
        )
    )
}
