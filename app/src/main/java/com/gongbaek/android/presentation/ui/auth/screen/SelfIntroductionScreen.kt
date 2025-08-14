package com.gongbaek.android.presentation.ui.auth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.gongbaek.android.R
import com.gongbaek.android.presentation.type.GongBaekBasicTextFieldType
import com.gongbaek.android.presentation.ui.auth.state.SelfIntroductionState
import com.gongbaek.android.presentation.ui.component.button.GongBaekBasicButton
import com.gongbaek.android.presentation.ui.component.progressBar.GongBaekProgressBar
import com.gongbaek.android.presentation.ui.component.section.PageDescriptionSection
import com.gongbaek.android.presentation.ui.component.textfield.GongBaekBasicTextField
import com.gongbaek.android.presentation.ui.component.topbar.StartTitleTopBar
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun SelfIntroductionRoute(
    viewModel: AuthViewModel,
    navigateEnterTimetable: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.setEvent(AuthContract.Event.ClearSelfIntroduction)
        viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AuthContract.SideEffect.NavigateEnterTimetable -> navigateEnterTimetable()
                    is AuthContract.SideEffect.NavigateBack -> navigateBack()
                    else -> {}
                }
            }
    }

    SelfIntroductionScreen(
        uiState = uiState.selfIntroductionState,
        onSelfIntroductionChanged = { selfIntroduction -> viewModel.setEvent(AuthContract.Event.SelfIntroductionChanged(selfIntroduction)) },
        onNextClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateEnterTimetable) },
        onBackClick = {
            viewModel.setEvent(AuthContract.Event.ClearSelfIntroduction)
            viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack)
        }
    )
}

@Composable
private fun SelfIntroductionScreen(
    uiState: SelfIntroductionState,
    onSelfIntroductionChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickableWithoutRipple {
                focusManager.clearFocus()
            }
    ) {
        SelfIntroductionSection(
            selfIntroduction = uiState.selfIntroduction,
            onSelfIntroductionChanged = onSelfIntroductionChanged,
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
        )

        GongBaekBasicButton(
            title = stringResource(R.string.auth_self_introduction_button_next),
            enabled = uiState.isNextEnabled,
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun SelfIntroductionSection(
    selfIntroduction: String,
    onSelfIntroductionChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        StartTitleTopBar(onLeadingIconClick = onBackClick)

        GongBaekProgressBar(progressPercent = 6 / 7f)

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            PageDescriptionSection(
                titleResId = R.string.auth_self_introduction_title
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.auth_self_introduction_description))

                    addStyle(
                        style = SpanStyle(
                            color = GongBaekTheme.colors.mainOrange
                        ),
                        start = 17,
                        end = 27
                    )
                },
                color = GongBaekTheme.colors.gray07,
                style = GongBaekTheme.typography.body1.m16
            )

            Spacer(modifier = Modifier.height(22.dp))

            GongBaekBasicTextField(
                value = selfIntroduction,
                onValueChange = onSelfIntroductionChanged,
                gongBaekBasicTextFieldType = GongBaekBasicTextFieldType.SELF_INTRODUCTION,
                modifier = Modifier
                    .height((LocalConfiguration.current.screenHeightDp * 0.169f).dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelfIntroductionScreenPreview() {
    GONGBAEKTheme {
        SelfIntroductionScreen(
            uiState = SelfIntroductionState(),
            onSelfIntroductionChanged = {},
            onNextClick = {},
            onBackClick = {}
        )
    }
}
