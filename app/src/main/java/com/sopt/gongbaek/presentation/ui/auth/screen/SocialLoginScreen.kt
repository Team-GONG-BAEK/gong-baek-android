package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun SocialLoginScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GongBaekTheme.colors.gray10),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = buildAnnotatedString {
                append("공강을 백으로 채우다")

                addStyle(
                    style = SpanStyle(
                        color = GongBaekTheme.colors.mainOrange,
                        fontSize = GongBaekTheme.typography.body2.sb14.fontSize,
                        fontWeight = GongBaekTheme.typography.body2.sb14.fontWeight,
                        fontFamily = GongBaekTheme.typography.body2.sb14.fontFamily,
                        letterSpacing = GongBaekTheme.typography.body2.sb14.letterSpacing
                    ),
                    start = 0,
                    end = 1
                )

                addStyle(
                    style = SpanStyle(
                        color = GongBaekTheme.colors.mainOrange,
                        fontSize = GongBaekTheme.typography.body2.sb14.fontSize,
                        fontWeight = GongBaekTheme.typography.body2.sb14.fontWeight,
                        fontFamily = GongBaekTheme.typography.body2.sb14.fontFamily,
                        letterSpacing = GongBaekTheme.typography.body2.sb14.letterSpacing
                    ),
                    start = 4,
                    end = 5
                )
            },
            color = GongBaekTheme.colors.white,
            style = GongBaekTheme.typography.body2.sb14
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.img_kakao_login_btn),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SocialLoginScreenPreview() {
    GONGBAEKTheme {
        SocialLoginScreen()
    }
}