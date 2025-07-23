package com.sopt.gongbaek.presentation.ui.component.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme
import kotlinx.coroutines.delay

@Composable
fun GongBaekToastMessage(
    iconResId: Int,
    message: String,
    durationMillis: Long,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        visible = false
        onDismiss()
    }

    AnimatedVisibility(
        visible = visible,
        exit = fadeOut() + slideOutVertically()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(44.dp)
                .background(color = GongBaekTheme.colors.black, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = message,
                    color = GongBaekTheme.colors.white,
                    style = GongBaekTheme.typography.body2.r14
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupReportToastMessagePreview() {
    GONGBAEKTheme {
        GongBaekToastMessage(
            iconResId = R.drawable.ic_check_fill_24,
            message = "해당 모임 신고가 완료되었습니다.",
            durationMillis = 2000,
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentReportToastMessagePreview() {
    GONGBAEKTheme {
        GongBaekToastMessage(
            iconResId = R.drawable.ic_check_fill_24,
            message = "해당 댓글 신고가 완료되었습니다.",
            durationMillis = 2000,
            onDismiss = {}
        )
    }
}
