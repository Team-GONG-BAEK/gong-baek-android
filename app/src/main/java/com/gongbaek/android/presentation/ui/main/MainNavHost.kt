package com.gongbaek.android.presentation.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.ui.auth.navigation.authNavGraph
import com.gongbaek.android.presentation.ui.groupdetail.navigation.groupDetailNavGraph
import com.gongbaek.android.presentation.ui.grouplist.navigation.groupListNavGraph
import com.gongbaek.android.presentation.ui.groupregister.navigation.groupRegisterNavGraph
import com.gongbaek.android.presentation.ui.grouproom.navigation.groupRoomNavGraph
import com.gongbaek.android.presentation.ui.home.navigation.homeNavGraph
import com.gongbaek.android.presentation.ui.login.SocialLoginRoute
import com.gongbaek.android.presentation.ui.mypage.navigation.myPageNavGraph
import com.gongbaek.android.presentation.ui.onboarding.navigation.onboardingNavGraph
import com.gongbaek.android.presentation.ui.onboarding.screen.TermsOfServiceRoute
import com.gongbaek.android.presentation.ui.splash.SplashRoute

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
        authNavGraph(
            navController = navigator.navController,
            navigateBack = navigator::navigateBack
        )
        groupListNavGraph(
            navController = navigator.navController,
            innerPadding = paddingValues
        )
        groupRegisterNavGraph(
            navController = navigator.navController,
            navigateBack = navigator::navigateBack
        )
        groupDetailNavGraph(
            navController = navigator.navController,
            navigateBack = navigator::navigateBack
        )
        homeNavGraph(
            navController = navigator.navController,
            innerPadding = paddingValues
        )
        groupRoomNavGraph(
            navController = navigator.navController,
            navigateBack = navigator::navigateBack
        )
        myPageNavGraph(
            navController = navigator.navController,
            navigateBack = navigator::navigateBack,
            innerPadding = paddingValues
        )
    }
}
