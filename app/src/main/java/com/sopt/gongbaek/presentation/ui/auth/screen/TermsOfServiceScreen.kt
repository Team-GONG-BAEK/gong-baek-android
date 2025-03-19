package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun TermsOfServiceScreen(

) {
    var termsOfService by remember { mutableStateOf(false) }
    var privacyPolicy by remember { mutableStateOf(false) }
    var marketingPolicy by remember { mutableStateOf(false) }
    val fullAcceptance = termsOfService && privacyPolicy && marketingPolicy

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StartTitleTopBar(
            startTitleResId = R.string.terms_of_service_title,
            onClick = { /* 소셜 로그인 화면으로 이동 */ }
        )
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector =
                if (fullAcceptance) ImageVector.vectorResource(id = R.drawable.ic_radio_btn_fill)
                else ImageVector.vectorResource(id = R.drawable.ic_radio_btn_unfill),
                contentDescription = null,
                modifier = Modifier.clickableWithoutRipple {
                    val newState = !fullAcceptance
                    termsOfService = newState
                    privacyPolicy = newState
                    marketingPolicy = newState
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "약관 전체동의",
                color = GongBaekTheme.colors.black,
                style = GongBaekTheme.typography.title2.m18
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = GongBaekTheme.colors.gray02
        )

        Spacer(modifier = Modifier.height(20.dp))
        ServiceAcceptanceSection(
            description = "[필수] 서비스 이용약관",
            acceptance = termsOfService,
            onAcceptClick = { termsOfService = !termsOfService },
            onDetailClick = { /* 노션 페이지로 이동 */ }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ServiceAcceptanceSection(
            description = "[필수] 개인정보 처리 방침",
            acceptance = privacyPolicy,
            onAcceptClick = { privacyPolicy = !privacyPolicy },
            onDetailClick = { /* 노션 페이지로 이동 */ }
        )
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = GongBaekTheme.colors.gray02
        )

        Spacer(modifier = Modifier.height(20.dp))
        ServiceAcceptanceSection(
            description = "[선택] 마케팅 정보 수신 동의",
            acceptance = marketingPolicy,
            onAcceptClick = { marketingPolicy = !marketingPolicy },
            onDetailClick = { /* 노션 페이지로 이동 */ }
        )

        Spacer(modifier = Modifier.weight(1f))
        GongBaekBasicButton(
            title = "다음",
            onClick = { /* 다음 페이지로 이동 */ },
            enabled = termsOfService && privacyPolicy,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}

@Composable
fun ServiceAcceptanceSection(
    description: String,
    acceptance: Boolean,
    onAcceptClick: () -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector =
            if (acceptance) ImageVector.vectorResource(id = R.drawable.ic_check_fill)
            else ImageVector.vectorResource(id = R.drawable.ic_check_unfill),
            contentDescription = null,
            modifier = Modifier.clickableWithoutRipple { onAcceptClick() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = description,
            color = GongBaekTheme.colors.gray09,
            style = GongBaekTheme.typography.body2.m14
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_32),
            contentDescription = null,
            modifier = Modifier.clickableWithoutRipple { onDetailClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TermsOfServiceScreenPreview() {
    GONGBAEKTheme {
        TermsOfServiceScreen()
    }
}
