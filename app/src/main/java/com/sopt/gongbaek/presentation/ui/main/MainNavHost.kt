package com.sopt.gongbaek.presentation.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.auth.navigation.authNavGraph
import com.sopt.gongbaek.presentation.ui.groupdetail.navigation.groupDetailNavGraph
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.groupListNavGraph
import com.sopt.gongbaek.presentation.ui.groupregister.navigation.groupRegisterNavGraph
import com.sopt.gongbaek.presentation.ui.grouproom.navigation.groupRoomNavGraph
import com.sopt.gongbaek.presentation.ui.home.navigation.homeNavGraph
import com.sopt.gongbaek.presentation.ui.login.SocialLoginRoute
import com.sopt.gongbaek.presentation.ui.mypage.navigation.myPageNavGraph
import com.sopt.gongbaek.presentation.ui.onboarding.navigation.onboardingNavGraph
import com.sopt.gongbaek.presentation.ui.onboarding.screen.TermsOfServiceRoute
import com.sopt.gongbaek.presentation.ui.splash.SplashRoute

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable<NavigationRoute.Splash> { SplashRoute(navController = navigator.navController) }
        composable<NavigationRoute.Login> { SocialLoginRoute(navController = navigator.navController) }
        composable<NavigationRoute.TermsOfService> { TermsOfServiceRoute(navController = navigator.navController) }
        onboardingNavGraph(navigator.navController)
        authNavGraph(navigator.navController)
        groupListNavGraph(
            navController = navigator.navController,
            innerPadding = paddingValues
        )
        groupRegisterNavGraph(navigator.navController)
        groupDetailNavGraph(navigator.navController)
        homeNavGraph(
            navController = navigator.navController,
            innerPadding = paddingValues
        )
        groupRoomNavGraph(navigator.navController)
        myPageNavGraph(
            navController = navigator.navController,
            innerPadding = paddingValues
        )
    }
}
