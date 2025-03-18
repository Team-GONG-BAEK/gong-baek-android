package com.sopt.gongbaek.presentation.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.auth.navigation.authNavGraph
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.groupDetailNavGraph
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.groupListNavGraph
import com.sopt.gongbaek.presentation.ui.groupregister.navigation.groupRegisterNavGraph
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.groupRoomNavGraph
import com.sopt.gongbaek.presentation.ui.home.navigation.homeNavGraph
import com.sopt.gongbaek.presentation.ui.mypage.navigation.myPageNavGraph
import com.sopt.gongbaek.presentation.ui.onboarding.navigation.onboardingNavGraph
import com.sopt.gongbaek.presentation.ui.splash.SplashScreen

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navigator.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
            .fillMaxSize()
            .padding(
                if (currentRoute == NavigationRoute.MainBottomNavBarTabRoute.HOME_TAB ||
                    currentRoute == NavigationRoute.GROUP_ROOM
                ) {
                    PaddingValues(0.dp)
                } else {
                    WindowInsets.statusBars.asPaddingValues()
                }
            )
    ) {
        composable(NavigationRoute.SPLASH) { SplashScreen(navController = navigator.navController) }
        onboardingNavGraph(navigator.navController)
        authNavGraph(navigator.navController)
        groupListNavGraph(navigator.navController)
        groupRegisterNavGraph(navigator.navController)
        groupDetailNavGraph(navigator.navController)
        homeNavGraph(navigator.navController)
        groupRoomNavGraph(navigator.navController)
        myPageNavGraph(navigator.navController)
//        myGroupNavGraph(navigator.navController)
    }
}
