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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.type.GongBaekDialogType
import com.sopt.gongbaek.presentation.type.SettingServiceGuideItem
import com.sopt.gongbaek.presentation.ui.component.dialog.AccountDialog
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.presentation.util.openWebView
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun SettingRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateLogin: () -> Unit
) {
    val myPageUiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyPageContract.SideEffect.NavigateBack -> navigateBack()
                    is MyPageContract.SideEffect.NavigateLogin -> navigateLogin()
                    else -> {}
                }
            }
    }

    SettingScreen(
        uiState = myPageUiState,
        context = context,
        onBackClick = { viewModel.sendSideEffect(MyPageContract.SideEffect.NavigateBack) },
        onLogoutClicked = {
            viewModel.setEvent(MyPageContract.Event.OnLogoutClicked)
        },
        onWithdrawClicked = {
            viewModel.setEvent(MyPageContract.Event.OnWithdrawClicked)
        },
        onLogoutDialogConfirmButtonClicked = {
            viewModel.setEvent(MyPageContract.Event.OnLogoutDialogConfirmClicked)
        },
        onLogoutDialogDismissClicked = {
            viewModel.setEvent(MyPageContract.Event.OnLogoutDialogDismissClicked)
        },
        onWithdrawDialogConfirmButtonClicked = {
            viewModel.setEvent(MyPageContract.Event.OnWithdrawDialogConfirmClicked)
        },
        onWithdrawDialogDismissClicked = {
            viewModel.setEvent(MyPageContract.Event.OnWithdrawDialogDismissClicked)
        }
    )
}

@Composable
private fun SettingScreen(
    uiState: MyPageContract.State,
    context: Context,
    onBackClick: () -> Unit,
    onLogoutClicked: () -> Unit,
    onWithdrawClicked: () -> Unit,
    onLogoutDialogConfirmButtonClicked: () -> Unit,
    onLogoutDialogDismissClicked: () -> Unit,
    onWithdrawDialogConfirmButtonClicked: () -> Unit,
    onWithdrawDialogDismissClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StartTitleTopBar(
            startTitleResId = R.string.topbar_setting,
            onLeadingIconClick = onBackClick
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
            onLogoutClicked = onLogoutClicked,
            onWithdrawClicked = onWithdrawClicked,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (uiState.logoutDialogState == true) {
            Dialog(
                onDismissRequest = onLogoutDialogDismissClicked
            ) {
                AccountDialog(
                    gongBaekDialogType = GongBaekDialogType.LOGOUT,
                    onConfirmButtonClick = onLogoutDialogConfirmButtonClicked,
                    onDismissButtonClick = onLogoutDialogDismissClicked
                )
            }
        }
        if (uiState.withdrawDialogState == true) {
            Dialog(
                onDismissRequest = onWithdrawDialogDismissClicked
            ) {
                AccountDialog(
                    gongBaekDialogType = GongBaekDialogType.WITHDRAW,
                    onConfirmButtonClick = onWithdrawDialogConfirmButtonClicked,
                    onDismissButtonClick = onWithdrawDialogDismissClicked
                )
            }
        }
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

        SettingServiceGuideItem.entries.forEach { item ->
            if (item.url != null) {
                Text(
                    text = stringResource(item.titleResId),
                    style = GongBaekTheme.typography.body1.r16,
                    color = GongBaekTheme.colors.gray10,
                    modifier = Modifier
                        .clickableWithoutRipple {
                            openWebView(context, item.url)
                        }
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(item.titleResId),
                        style = GongBaekTheme.typography.body1.r16,
                        color = GongBaekTheme.colors.gray10,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )

                    Text(
                        text = uiState.versionName,
                        style = GongBaekTheme.typography.body1.r16,
                        color = GongBaekTheme.colors.gray04
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountSection(
    onLogoutClicked: () -> Unit,
    onWithdrawClicked: () -> Unit,
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
                .clickableWithoutRipple { onLogoutClicked() }
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )

        Text(
            text = stringResource(R.string.setting_withdraw),
            style = GongBaekTheme.typography.body1.r16,
            color = GongBaekTheme.colors.gray10,
            modifier = Modifier
                .clickableWithoutRipple { onWithdrawClicked() }
                .fillMaxWidth()
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
        onBackClick = {},
        onLogoutClicked = {},
        onWithdrawClicked = {},
        onLogoutDialogConfirmButtonClicked = {},
        onLogoutDialogDismissClicked = {},
        onWithdrawDialogConfirmButtonClicked = {},
        onWithdrawDialogDismissClicked = {}
    )
}
