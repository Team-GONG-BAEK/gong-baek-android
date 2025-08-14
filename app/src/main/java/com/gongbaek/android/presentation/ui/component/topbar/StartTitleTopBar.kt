package com.gongbaek.android.presentation.ui.component.topbar

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.gongbaek.android.R
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun StartTitleTopBar(
    modifier: Modifier = Modifier,
    @StringRes startTitleResId: Int? = null,
    isLeadingIconIncluded: Boolean = true,
    isTrailingIconIncluded: Boolean = false,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .aspectRatio(7.5f)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLeadingIconIncluded) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_48),
                contentDescription = null,
                tint = GongBaekTheme.colors.gray04,
                modifier = Modifier.clickableWithoutRipple {
                    onLeadingIconClick()
                }
            )
        }

        if (startTitleResId != null) {
            Text(
                text = stringResource(startTitleResId),
                color = GongBaekTheme.colors.gray08,
                style = GongBaekTheme.typography.title2.m18
            )
        }

        if (isTrailingIconIncluded) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_report_48),
                contentDescription = null,
                tint = GongBaekTheme.colors.gray04,
                modifier = Modifier.clickableWithoutRipple {
                    onTrailingIconClick()
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewStartTitleTopBar() {
    Column(
        modifier = Modifier
            .background(color = GongBaekTheme.colors.white)
    ) {
        StartTitleTopBar()
        StartTitleTopBar(isLeadingIconIncluded = false)
        StartTitleTopBar(startTitleResId = R.string.topbar_group, isTrailingIconIncluded = true)
        StartTitleTopBar(startTitleResId = R.string.topbar_my_group)
    }
}
