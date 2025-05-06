package com.sopt.gongbaek.presentation.ui.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.type.SettingServiceGuideItem
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.presentation.util.openWebView
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun TermsOfServiceRoute(
    navController: NavHostController,
    viewModel: TermsOfServiceViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is TermsOfServiceContract.SideEffect.OnBackClick -> {
                        navController.navigate(NavigationRoute.Login) {
                            popUpTo(NavigationRoute.TermsOfService) { inclusive = true }
                        }
                    }
                    is TermsOfServiceContract.SideEffect.OnTermsOfServiceDetailClick -> {
                        openWebView(context, SettingServiceGuideItem.TERMS_OF_SERVICE.url.orEmpty())
                    }
                    is TermsOfServiceContract.SideEffect.OnPrivacyPolicyDetailClick -> {
                        openWebView(context, SettingServiceGuideItem.PRIVACY_POLICY.url.orEmpty())
                    }
                    is TermsOfServiceContract.SideEffect.OnNextClick -> {
                        navController.navigate(NavigationRoute.Onboarding)
                    }
                }
            }
    }

    TermsOfServiceScreen(
        uiState = uiState,
        onBackClick = { viewModel.sendSideEffect(TermsOfServiceContract.SideEffect.OnBackClick) },
        onFullAcceptClick = { viewModel.setEvent(TermsOfServiceContract.Event.OnFullAcceptClick) },
        onTermsOfServiceClick = { viewModel.setEvent(TermsOfServiceContract.Event.OnTermsOfServiceClick) },
        onTermsOfServiceDetailClick = { viewModel.sendSideEffect(TermsOfServiceContract.SideEffect.OnTermsOfServiceDetailClick) },
        onPrivacyPolicyClick = { viewModel.setEvent(TermsOfServiceContract.Event.OnPrivacyPolicyClick) },
        onPrivacyPolicyDetailClick = { viewModel.sendSideEffect(TermsOfServiceContract.SideEffect.OnPrivacyPolicyDetailClick) },
        onNextClick = { viewModel.sendSideEffect(TermsOfServiceContract.SideEffect.OnNextClick) }
    )
}

@Composable
private fun TermsOfServiceScreen(
    uiState: TermsOfServiceContract.State,
    onBackClick: () -> Unit,
    onFullAcceptClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    onTermsOfServiceDetailClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onPrivacyPolicyDetailClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StartTitleTopBar(
            startTitleResId = R.string.terms_of_service_title,
            onLeadingIconClick = onBackClick
        )
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector =
                if (uiState.fullAcceptance) {
                    ImageVector.vectorResource(id = R.drawable.ic_radio_btn_fill)
                } else {
                    ImageVector.vectorResource(id = R.drawable.ic_radio_btn_unfill)
                },
                contentDescription = null,
                modifier = Modifier.clickableWithoutRipple { onFullAcceptClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.terms_of_service_full_acceptance),
                color = GongBaekTheme.colors.black,
                style = GongBaekTheme.typography.title2.m18
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = GongBaekTheme.colors.gray02
        )

        Spacer(modifier = Modifier.height(20.dp))
        ServiceAcceptanceSection(
            description = stringResource(R.string.terms_of_service_terms_of_service),
            acceptance = uiState.termsOfService,
            onAcceptClick = onTermsOfServiceClick,
            onDetailClick = onTermsOfServiceDetailClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        ServiceAcceptanceSection(
            description = stringResource(R.string.terms_of_service_privacy_policy),
            acceptance = uiState.privacyPolicy,
            onAcceptClick = onPrivacyPolicyClick,
            onDetailClick = onPrivacyPolicyDetailClick
        )

        Spacer(modifier = Modifier.weight(1f))
        GongBaekBasicButton(
            title = stringResource(R.string.terms_of_service_next),
            onClick = onNextClick,
            enabled = uiState.termsOfService && uiState.privacyPolicy,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}

@Composable
fun ServiceAcceptanceSection(
    description: String,
    acceptance: Boolean,
    onAcceptClick: () -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector =
            if (acceptance) {
                ImageVector.vectorResource(id = R.drawable.ic_check_fill)
            } else {
                ImageVector.vectorResource(id = R.drawable.ic_check_unfill)
            },
            contentDescription = null,
            modifier = Modifier.clickableWithoutRipple { onAcceptClick() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = description,
            color = GongBaekTheme.colors.gray09,
            style = GongBaekTheme.typography.body2.m14
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_32),
            contentDescription = null,
            modifier = Modifier.clickableWithoutRipple { onDetailClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TermsOfServiceScreenPreview() {
    GONGBAEKTheme {
        TermsOfServiceScreen(
            uiState = TermsOfServiceContract.State(),
            onBackClick = {},
            onFullAcceptClick = {},
            onTermsOfServiceClick = {},
            onTermsOfServiceDetailClick = {},
            onPrivacyPolicyClick = {},
            onPrivacyPolicyDetailClick = {},
            onNextClick = {}
        )
    }
}
