package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmailVerificationRoute() {
    EmailVerificationScreen()
}

@Composable
private fun EmailVerificationScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("여긴 이메일 인증화면")
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailVerificationScreenPreview() {
    EmailVerificationScreen()
}
