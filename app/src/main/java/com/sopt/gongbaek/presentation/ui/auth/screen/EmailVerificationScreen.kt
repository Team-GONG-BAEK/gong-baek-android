package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmailVerificationRoute(
    viewModel: AuthViewModel,
    navigateNicknameGender: () -> Unit
) {
    EmailVerificationScreen(
        onClick = navigateNicknameGender
    )
}

@Composable
private fun EmailVerificationScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("여긴 이메일 인증화면")

        Button(
            onClick = onClick
        ) { Text("닉네임 입력으로 이동해보던가") }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailVerificationScreenPreview() {
    EmailVerificationScreen(
        onClick = {}
    )
}
