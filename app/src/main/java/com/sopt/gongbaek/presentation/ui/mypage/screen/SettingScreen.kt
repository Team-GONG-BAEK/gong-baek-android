package com.sopt.gongbaek.presentation.ui.mypage.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.type.GongBaekWebView
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.presentation.util.openWebView
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun SettingRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val myPageUiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.getVersionInfo(context)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyPageContract.SideEffect.NavigateBack -> navigateBack()
                    else -> {}
                }
            }
    }

    SettingScreen(
        uiState = myPageUiState,
        context = context,
        onBackClick = { viewModel.sendSideEffect(MyPageContract.SideEffect.NavigateBack) }
    )
}

@Composable
private fun SettingScreen(
    uiState: MyPageContract.State,
    context: Context,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StartTitleTopBar(
            startTitleResId = R.string.topbar_setting,
            onClick = onBackClick
        )

        ServiceGuideSection(
            uiState = uiState,
            context = context,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 8.dp,
            color = GongBaekTheme.colors.gray02
        )

        AccountSection(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun ServiceGuideSection(
    uiState: MyPageContract.State,
    context: Context,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.setting_service_guide_title),
            style = GongBaekTheme.typography.title2.sb18,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Text(
            text = stringResource(R.string.setting_notice),
            style = GongBaekTheme.typography.body1.r16,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier
                .clickableWithoutRipple {
                    openWebView(context, GongBaekWebView.NOTICE.url)
                }
                .padding(vertical = 20.dp)
        )

        Text(
            text = stringResource(R.string.setting_privacy_policy),
            style = GongBaekTheme.typography.body1.r16,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier
                .clickableWithoutRipple {
                    openWebView(context, GongBaekWebView.PRIVACY_POLICY.url)
                }
                .padding(vertical = 20.dp)
        )

        Text(
            text = stringResource(R.string.setting_terms_of_service),
            style = GongBaekTheme.typography.body1.r16,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier
                .clickableWithoutRipple {
                    openWebView(context, GongBaekWebView.TERMS_OF_SERVICE.url)
                }
                .padding(vertical = 20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.setting_version_info),
                style = GongBaekTheme.typography.body1.r16,
                color = GongBaekTheme.colors.gray10,
                modifier = Modifier
                    .padding(vertical = 20.dp)
            )

            Text(
                text = uiState.versionName,
                style = GongBaekTheme.typography.body1.r16,
                color = GongBaekTheme.colors.gray04
            )
        }
    }
}

@Composable
private fun AccountSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.setting_account_title),
            style = GongBaekTheme.typography.title2.sb18,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Text(
            text = stringResource(R.string.setting_logout),
            style = GongBaekTheme.typography.body1.r16,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier
                .clickableWithoutRipple {}
                .padding(vertical = 20.dp)
        )

        Text(
            text = stringResource(R.string.setting_withdraw),
            style = GongBaekTheme.typography.body1.r16,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier
                .clickableWithoutRipple {}
                .padding(vertical = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        uiState = MyPageContract.State(),
        context = LocalContext.current,
        onBackClick = {}
    )
}
