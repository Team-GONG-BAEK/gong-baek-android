package com.gongbaek.android.presentation.ui.grouplist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.gongbaek.android.R
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun CycleBottomSheetPresenter(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = GongBaekTheme.colors.gray01,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickableWithoutRipple {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.grouplist_cycle_all),
            color = GongBaekTheme.colors.gray10,
            style = GongBaekTheme.typography.caption1.m13
        )
        Spacer(Modifier.width(6.dp))

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_bottom_18),
            contentDescription = null
        )
    }
}
