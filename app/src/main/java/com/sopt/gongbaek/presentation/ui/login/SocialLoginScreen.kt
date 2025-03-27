package com.sopt.gongbaek.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun SocialLoginRoute(
    navController: NavHostController,
    viewModel: SocialLoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.autoLogin) {
        if (uiState.autoLogin) {
            viewModel.sendSideEffect(SocialLoginContract.SideEffect.NavigateHome)
            viewModel.setEvent(SocialLoginContract.Event.ResetAutoLogin)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SocialLoginContract.SideEffect.NavigateHome -> {
                    navController.navigate(NavigationRoute.MainBottomNavBarTabRoute.HOME_TAB) {
                        popUpTo(NavigationRoute.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                is SocialLoginContract.SideEffect.NavigateTermsOfService -> {
                    navController.navigate(NavigationRoute.TERMS_OF_SERVICE) {
                        popUpTo(NavigationRoute.LOGIN) { inclusive = true }
                    }
                }
            }
        }
    }

    SocialLoginScreen(
        onLoginClick = { viewModel.setEvent(SocialLoginContract.Event.OnKaKaoLoginClick(context)) }
    )
}

@Composable
fun SocialLoginScreen(
    onLoginClick: () -> Unit
) {
    val highlightedTextStyle = GongBaekTheme.typography.body2.sb14
    val highlightedSpanStyle = SpanStyle(
        color = GongBaekTheme.colors.mainOrange,
        fontSize = highlightedTextStyle.fontSize,
        fontWeight = highlightedTextStyle.fontWeight,
        fontFamily = highlightedTextStyle.fontFamily,
        letterSpacing = highlightedTextStyle.letterSpacing
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GongBaekTheme.colors.gray10),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.login_screen_greeting))
                addStyle(
                    style = highlightedSpanStyle,
                    start = 0,
                    end = 1
                )
                addStyle(
                    style = highlightedSpanStyle,
                    start = 4,
                    end = 5
                )
            },
            color = GongBaekTheme.colors.white,
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
