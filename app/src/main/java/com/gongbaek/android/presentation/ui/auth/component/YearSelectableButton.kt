package com.gongbaek.android.presentation.ui.auth.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.gongbaek.android.R
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GongBaekTheme
import java.util.Calendar

@Composable
fun YearSelectableButton(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "입학년도",
            color = GongBaekTheme.colors.gray08,
            style = GongBaekTheme.typography.body2.sb14
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableWithoutRipple { showBottomSheet = true }
                .border(
                    width = 1.dp,
                    color = GongBaekTheme.colors.gray03,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedYear == 0) "${currentYear}년" else "${selectedYear}년",
                color = if (selectedYear == 0) GongBaekTheme.colors.gray04 else GongBaekTheme.colors.gray10,
                style = GongBaekTheme.typography.body1.m16
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_bottom_gray_24),
                contentDescription = null,
                tint = GongBaekTheme.colors.gray04
            )
        }

        if (showBottomSheet) {
            YearSelectionBottomSheet(
                selectedYear = selectedYear,
                currentYear = currentYear,
                onDismiss = { showBottomSheet = false },
                onYearSelected = {
                    onYearSelected(it)
                    showBottomSheet = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewYearSelectableButton() {
    YearSelectableButton(
        selectedYear = 0,
        onYearSelected = {}
    )
}
