package com.gongbaek.android.presentation.ui.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.MainBottomTabRoute
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.ui.groupdetail.navigation.navigateGroupDetail
import com.gongbaek.android.presentation.ui.grouproom.navigation.navigateGroupRoom
import com.gongbaek.android.presentation.ui.mypage.screen.MyPageRoute
import com.gongbaek.android.presentation.ui.mypage.screen.SettingRoute

fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    navigateBack: () -> Unit,
    innerPadding: PaddingValues
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
            },
            innerPadding = innerPadding
        )
    }

    composable<NavigationRoute.Setting> {
        SettingRoute(
            navigateBack = navigateBack,
            navigateLogin = {
                navController.navigate(NavigationRoute.Login) {
                    popUpTo(NavigationRoute.Setting) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}
