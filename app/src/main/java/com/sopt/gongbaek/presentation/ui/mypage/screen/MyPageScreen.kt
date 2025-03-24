package com.sopt.gongbaek.presentation.ui.mypage.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun MyPageRoute() {
    MyPageScreen()
}

@Composable
fun MyPageScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "여긴 마이페이지",
            fontSize = 30.sp
        )
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen()
}
