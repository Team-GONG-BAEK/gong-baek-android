package com.sopt.gongbaek.presentation.ui.groupdetail.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.NavigationRoute

fun NavController.navigateGroupDetail(groupId: Int, groupCycle: String) {
    navigate(
        NavigationRoute.GroupDetail(
            groupId = groupId,
            groupCycle = groupCycle
        )
    )
}
