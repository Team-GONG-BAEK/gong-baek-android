package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.ui.auth.state.EmailVerificationState
import com.sopt.gongbaek.presentation.ui.auth.state.EmailVerificationStep
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekButtonDefault
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.util.formatTimeLeft
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun EmailVerificationRoute(
    viewModel: AuthViewModel,
    navigateNicknameGender: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AuthContract.SideEffect.NavigateNicknameGender -> navigateNicknameGender()
                    is AuthContract.SideEffect.NavigateBack -> navigateBack()
                    else -> {}
                }
            }
    }

    EmailVerificationScreen(
        uiState = uiState.emailVerificationState,
        university = uiState.academicInfoState.university,
        onEmailChanged = { email -> viewModel.setEvent(AuthContract.Event.EmailChanged(email)) },
        onVerificationCodeRequested = { viewModel.setEvent(AuthContract.Event.VerificationCodeRequested) },
        onVerificationCodeChanged = { code -> viewModel.setEvent(AuthContract.Event.VerificationCodeChanged(code)) },
        onVerificationCodeSubmitted = { viewModel.setEvent(AuthContract.Event.VerificationCodeSubmitted) },
        onNextClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateNicknameGender) },
        onBackClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack) }
    )
}

@Composable
private fun EmailVerificationScreen(
    uiState: EmailVerificationState,
    university: String,
    onEmailChanged: (String) -> Unit,
    onVerificationCodeRequested: () -> Unit,
    onVerificationCodeChanged: (String) -> Unit,
    onVerificationCodeSubmitted: () -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        StartTitleTopBar(onClick = onBackClick)
        GongBaekProgressBar(progressPercent = 2 / 7f)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                val focusRequester = remember { FocusRequester() }

                Spacer(modifier = Modifier.height(54.dp))

                Text(
                    text = stringResource(R.string.auth_email_verification_title, university),
                    color = GongBaekTheme.colors.gray10,
                    style = GongBaekTheme.typography.head2.b24,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(42.dp))

                Column {
                    var isFocused by remember { mutableStateOf(false) }

                    Text(
                        text = stringResource(R.string.auth_email_input_title),
                        color = GongBaekTheme.colors.gray08,
                        style = GongBaekTheme.typography.body2.sb14,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Row(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        BasicTextField(
                            value = uiState.email,
                            onValueChange = onEmailChanged,
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    color = GongBaekTheme.colors.gray01,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        uiState.step == EmailVerificationStep.REQUEST_FAILED -> GongBaekTheme.colors.errorRed
                                        isFocused -> GongBaekTheme.colors.black
                                        else -> Color.Transparent
                                    },
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .onFocusChanged { focusState ->
                                    isFocused = focusState.isFocused
                                }
                                .wrapContentHeight()
                                .weight(0.675f)
                                .padding(
                                    vertical = 14.dp,
                                    horizontal = 16.dp
                                ),
                            textStyle = GongBaekTheme.typography.body1.m16.copy(
                                color = GongBaekTheme.colors.gray10
                            ),
                            singleLine = true,
                            cursorBrush = SolidColor(GongBaekTheme.colors.gray05)
                        ) { innerTextField ->
                            Box(
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (uiState.email.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.auth_email_input_placeholder),
                                        style = GongBaekTheme.typography.body1.m16,
                                        color = GongBaekTheme.colors.gray04,
                                        modifier = Modifier.align(Alignment.CenterStart)
                                    )
                                }
                                innerTextField()
                            }
                        }

                        GongBaekButton(
                            onClick = onVerificationCodeRequested,
                            colors = GongBaekButtonDefault.gongBaekButtonColors(
                                containerColor = GongBaekTheme.colors.black
                            ),
                            contentPadding = PaddingValues(vertical = 14.dp, horizontal = 12.dp),
                            modifier = Modifier.weight(0.213f)
                        ) {
                            Text(
                                text = if (uiState.step == EmailVerificationStep.INITIAL) stringResource(R.string.auth_email_button_request_code) else stringResource(R.string.auth_email_button_resend_code),
                                color = GongBaekTheme.colors.white,
                                style = GongBaekTheme.typography.body1.m16
                            )
                        }
                    }

                    Text(
                        text = uiState.emailMessage,
                        color = when (uiState.step) {
                            EmailVerificationStep.INITIAL -> Color.Transparent
                            EmailVerificationStep.REQUEST_FAILED -> GongBaekTheme.colors.errorRed
                            else -> GongBaekTheme.colors.gray08
                        },
                        style = GongBaekTheme.typography.caption2.r12,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Column {
                    var isFocused by remember { mutableStateOf(false) }

                    Text(
                        text = stringResource(R.string.auth_email_verification_code_title),
                        color = GongBaekTheme.colors.gray08,
                        style = GongBaekTheme.typography.body2.sb14,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        BasicTextField(
                            value = uiState.verificationCode,
                            onValueChange = onVerificationCodeChanged,
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    color = GongBaekTheme.colors.gray01,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        uiState.step == EmailVerificationStep.VERIFICATION_FAILED -> GongBaekTheme.colors.errorRed
                                        isFocused -> GongBaekTheme.colors.black
                                        else -> Color.Transparent
                                    },
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .onFocusChanged { focusState ->
                                    isFocused = focusState.isFocused
                                }
                                .wrapContentHeight()
                                .weight(0.675f)
                                .focusRequester(focusRequester)
                                .padding(
                                    vertical = 14.dp,
                                    horizontal = 16.dp
                                ),
                            singleLine = true,
                            textStyle = GongBaekTheme.typography.body1.m16.copy(
                                color = GongBaekTheme.colors.gray10
                            ),
                            cursorBrush = SolidColor(GongBaekTheme.colors.gray05)
                        ) { innerTextField ->
                            Box(
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (uiState.verificationCode.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.auth_email_verification_code_placeholder),
                                        style = GongBaekTheme.typography.body1.m16,
                                        color = GongBaekTheme.colors.gray04,
                                        modifier = Modifier.align(Alignment.CenterStart)
                                    )
                                }
                                Text(
                                    text = if (uiState.isTimerRunning) formatTimeLeft(uiState.timeLeft) else "",
                                    color = GongBaekTheme.colors.errorRed,
                                    style = GongBaekTheme.typography.caption2.r12,
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                innerTextField()
                            }
                        }

                        GongBaekButton(
                            onClick = onVerificationCodeSubmitted,
                            colors = GongBaekButtonDefault.gongBaekButtonColors(
                                containerColor = GongBaekTheme.colors.black
                            ),
                            enabled = uiState.isVerificationCodeEnabled,
                            contentPadding = PaddingValues(vertical = 14.dp, horizontal = 12.dp),
                            modifier = Modifier.weight(0.213f)
                        ) {
                            Text(
                                text = stringResource(R.string.auth_email_button_verify_code),
                                color = GongBaekTheme.colors.white,
                                style = GongBaekTheme.typography.body1.m16
                            )
                        }
                    }

                    Text(
                        text = uiState.verificationCodeMessage,
                        color = when (uiState.step) {
                            EmailVerificationStep.INITIAL -> Color.Transparent
                            EmailVerificationStep.VERIFICATION_FAILED -> GongBaekTheme.colors.errorRed
                            else -> GongBaekTheme.colors.gray08
                        },
                        style = GongBaekTheme.typography.caption2.r12,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            GongBaekBasicButton(
                title = stringResource(R.string.auth_email_button_next),
                enabled = uiState.isNextEnabled,
                onClick = onNextClick,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailVerificationScreenPreview() {
    EmailVerificationScreen(
        uiState = EmailVerificationState(),
        university = "공백대학교",
        onEmailChanged = {},
        onVerificationCodeRequested = {},
        onVerificationCodeChanged = {},
        onVerificationCodeSubmitted = {},
        onNextClick = {},
        onBackClick = {}
    )
}
