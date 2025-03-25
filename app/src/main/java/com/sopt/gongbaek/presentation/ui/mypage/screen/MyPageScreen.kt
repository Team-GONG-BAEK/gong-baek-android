package com.sopt.gongbaek.presentation.ui.mypage.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.ui.component.topbar.CenterTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun MyPageRoute() {
    MyPageScreen()
}

@Composable
fun MyPageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        CenterTitleTopBar(
            centerTitleResId = R.string.topbar_my_page,
            trailingIconResId = R.drawable.ic_setting_48,
            onTrailingIconClick = {},
            trailingIconColor = GongBaekTheme.colors.gray08
        )


    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    GONGBAEKTheme {
        MyPageScreen()
    }
}
