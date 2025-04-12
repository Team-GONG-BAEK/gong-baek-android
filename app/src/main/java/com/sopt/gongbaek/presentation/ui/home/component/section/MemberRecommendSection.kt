package com.sopt.gongbaek.presentation.ui.home.component.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun MemberRecommendSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "나와 딱 맞는 공백 멤버",
            color = GongBaekTheme.colors.gray10,
            style = GongBaekTheme.typography.title2.b18
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "공백 시간에 이 멤버들과 공백 활동 어때요?",
            color = GongBaekTheme.colors.gray06,
            style = GongBaekTheme.typography.body2.m14
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.img_home_comingsoon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(328f / 204f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemberRecommendSectionPreview() {
    MemberRecommendSection()
}
