package com.gongbaek.android.presentation.ui.groupdetail.navigation

import androidx.navigation.NavController
import com.gongbaek.android.presentation.model.NavigationRoute

fun NavController.navigateGroupDetail(groupId: Int, groupCycle: String) {
    navigate(
        NavigationRoute.GroupDetail(
            groupId = groupId,
            groupCycle = groupCycle
        )
    )
}
