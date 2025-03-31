package com.sopt.gongbaek.presentation.ui.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.navigateGroupRoom
import com.sopt.gongbaek.presentation.ui.mypage.screen.MyPageRoute
import com.sopt.gongbaek.presentation.ui.setting.navigation.navigateSetting

fun NavGraphBuilder.myPageNavGraph(
    navController: NavController
) {
    composable<MainBottomTabRoute.MyPage> {
        MyPageRoute(
            navigateSetting = {
                navController.navigateSetting()
            },
            navigateGroupDetail = { groupId, groupCycle ->
                navController.navigateGroupDetail(groupId, groupCycle)
            },
            navigateGroupRoom = { groupId, groupCycle ->
                navController.navigateGroupRoom(groupId, groupCycle)
            }
        )
    }
}
