package com.gongbaek.android.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gongbaek.android.R
import com.gongbaek.android.presentation.model.MainBottomTabRoute
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun SocialLoginRoute(
    navController: NavHostController,
    viewModel: SocialLoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SocialLoginContract.SideEffect.NavigateHome -> {
                        navController.navigate(MainBottomTabRoute.Home) {
                            popUpTo(NavigationRoute.Login) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    is SocialLoginContract.SideEffect.NavigateTermsOfService -> {
                        navController.navigate(NavigationRoute.TermsOfService) {
                            popUpTo(NavigationRoute.Login) { inclusive = true }
                        }
                    }
                }
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            systemUiController.setStatusBarColor(color = Color.White)
        }
    }

    SocialLoginScreen(
        onLoginClick = { viewModel.setEvent(SocialLoginContract.Event.OnKaKaoLoginClick(context)) }
    )
}

@Composable
private fun SocialLoginScreen(
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GongBaekTheme.colors.gray10),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.15f))
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = null,
            modifier = Modifier.size(74.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.login_screen_greeting))
                addStyle(
                    style = SpanStyle(
                        color = GongBaekTheme.colors.mainOrange
                    ),
                    start = 0,
                    end = 1
                )
                addStyle(
                    style = SpanStyle(
                        color = GongBaekTheme.colors.mainOrange
                    ),
                    start = 4,
                    end = 5
                )
            },
            color = GongBaekTheme.colors.gray02,
            style = GongBaekTheme.typography.body2.sb14
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.img_kakao_login_btn),
            contentDescription = null,
            modifier = Modifier.clickableWithoutRipple { onLoginClick() }
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SocialLoginScreenPreview() {
    GONGBAEKTheme {
        SocialLoginScreen(
            onLoginClick = {}
        )
    }
}
