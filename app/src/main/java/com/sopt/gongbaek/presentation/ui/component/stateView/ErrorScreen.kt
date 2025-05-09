package com.sopt.gongbaek.presentation.ui.component.stateView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun ErrorScreen(
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_dialog_fail),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(R.string.error_title),
            style = GongBaekTheme.typography.caption1.m13,
            color = GongBaekTheme.colors.gray08
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .background(
                    color = GongBaekTheme.colors.mainOrange,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable(
                    role = Role.Button,
                    onClick = onClickRetry
                )
                .height(30.dp)
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.error_retry_button),
                style = GongBaekTheme.typography.caption2.b12,
                color = GongBaekTheme.colors.white
            )
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(
        onClickRetry = { }
    )
}
