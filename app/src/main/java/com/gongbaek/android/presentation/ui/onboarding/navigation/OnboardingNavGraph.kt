package com.gongbaek.android.presentation.ui.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.ui.auth.navigation.navigateAuthRoute
import com.gongbaek.android.presentation.ui.onboarding.screen.OnboardingRoute

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController
) {
    composable<NavigationRoute.Onboarding> {
        OnboardingRoute(
            navigateAuth = navController::navigateAuthRoute
        )
    }
}
