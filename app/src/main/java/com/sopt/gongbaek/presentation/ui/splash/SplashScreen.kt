package com.sopt.gongbaek.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.model.MainBottomTabRoute
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.util.GongBaekLottieAnimation
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun SplashRoute(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val systemUiController = rememberSystemUiController()
    val backgroundColor = GongBaekTheme.colors.gray10

    LaunchedEffect(uiState.autoLogin) {
        if (uiState.autoLogin == true) {
            viewModel.sendSideEffect(SplashContract.SideEffect.NavigateHome)
        } else {
            viewModel.sendSideEffect(SplashContract.SideEffect.NavigateSocialLogin)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SplashContract.SideEffect.NavigateHome -> {
                        navController.navigate(MainBottomTabRoute.Home) {
                            popUpTo(NavigationRoute.Splash) { inclusive = true }
                            launchSingleTop = true
                        }
                        viewModel.setEvent(SplashContract.Event.ResetAutoLogin)
                    }

                    is SplashContract.SideEffect.NavigateSocialLogin -> {
                        navController.navigate(NavigationRoute.Login) {
                            popUpTo(NavigationRoute.Splash) { inclusive = true }
                        }
                        viewModel.setEvent(SplashContract.Event.ResetAutoLogin)
                    }
                }
            }
    }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    SplashScreen(
        onComplete = { viewModel.setEvent(SplashContract.Event.ValidateAutoLogin) }
    )
}

@Composable
private fun SplashScreen(
    onComplete: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = GongBaekTheme.colors.gray10)
            .padding(end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        GongBaekLottieAnimation(
            lottieJson = R.raw.lottie_splash,
            onComplete = onComplete,
            modifier = Modifier.fillMaxSize(),
            maxFrame = 120
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    GONGBAEKTheme {
        SplashScreen(onComplete = {})
    }
}
