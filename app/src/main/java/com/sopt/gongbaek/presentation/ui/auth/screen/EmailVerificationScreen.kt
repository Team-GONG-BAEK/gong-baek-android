package com.sopt.gongbaek.presentation.ui.auth.screen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekButtonDefault
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.textfield.GongBaekTextField
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun EmailVerificationRoute(
    viewModel: AuthViewModel,
    navigateNicknameGender: () -> Unit,
    navigateBack: () -> Unit
) {
    EmailVerificationScreen(
        onClick = navigateNicknameGender,
        onBackClick = navigateBack
    )
}

@Composable
private fun EmailVerificationScreen(
    onClick: () -> Unit,
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
                Spacer(modifier = Modifier.height(54.dp))

                Text(
                    text = stringResource(R.string.auth_email_verification_title, "건국대학교"),
                    color = GongBaekTheme.colors.gray10,
                    style = GongBaekTheme.typography.head2.b24,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(42.dp))

                Text(
                    text = "이메일 주소",
                    color = GongBaekTheme.colors.gray08,
                    style = GongBaekTheme.typography.body2.sb14,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GongBaekTextField(
                        value = "",
                        onValueChanged = {},
                        modifier = Modifier.weight(0.675f)
                    )

                    GongBaekButton(
                        onClick = {},
                        colors = GongBaekButtonDefault.gongBaekButtonColors(
                            containerColor = GongBaekTheme.colors.black
                        ),
                        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 12.dp),
                        modifier = Modifier.weight(0.213f)
                    ) {
                        Text(
                            text = "코드받기",
                            color = GongBaekTheme.colors.white,
                            style = GongBaekTheme.typography.body1.m16
                        )
                    }
                }

                Spacer(modifier = Modifier.height(34.dp))

                Text(
                    text = "코드",
                    color = GongBaekTheme.colors.gray08,
                    style = GongBaekTheme.typography.body2.sb14,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GongBaekTextField(
                        value = "",
                        onValueChanged = {},
                        modifier = Modifier.weight(0.675f)
                    )

                    GongBaekButton(
                        onClick = {},
                        colors = GongBaekButtonDefault.gongBaekButtonColors(
                            containerColor = GongBaekTheme.colors.black
                        ),
                        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 12.dp),
                        modifier = Modifier.weight(0.213f)
                    ) {
                        Text(
                            text = "인증하기",
                            color = GongBaekTheme.colors.white,
                            style = GongBaekTheme.typography.body1.m16
                        )
                    }
                }
            }

            GongBaekBasicButton(
                title = "다음",
                enabled = true,
                onClick = onClick,
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
        onClick = {},
        onBackClick = {}
    )
}
