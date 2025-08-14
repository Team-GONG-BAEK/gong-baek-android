package com.gongbaek.android.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongbaek.android.presentation.type.MainBottomNavBarTabType
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun MainBottomNavBar(
    isVisible: Boolean,
    bottomNavBarTabTypes: List<MainBottomNavBarTabType>,
    currentBottomNavBarTab: MainBottomNavBarTabType?,
    onBottomNavBarTabSelected: (MainBottomNavBarTabType) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        Column {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = GongBaekTheme.colors.gray02
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = GongBaekTheme.colors.white),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomNavBarTabTypes.forEach { bottomNavBarTabType ->
                    MainBottomNavBarItem(
                        bottomNavBarTabType = bottomNavBarTabType,
                        isSelected = currentBottomNavBarTab == bottomNavBarTabType,
                        onClick = { onBottomNavBarTabSelected(bottomNavBarTabType) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MainBottomNavBarItem(
    bottomNavBarTabType: MainBottomNavBarTabType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickableWithoutRipple(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(
                id = if (isSelected) {
                    bottomNavBarTabType.selectedIconRes
                } else {
                    bottomNavBarTabType.unselectedIconRes
                }
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(bottomNavBarTabType.label),
            style = GongBaekTheme.typography.body2.m14,
            color = if (isSelected) GongBaekTheme.colors.gray10 else GongBaekTheme.colors.gray05
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomNavBarPreview() {
    GONGBAEKTheme {
        MainBottomNavBar(
            isVisible = true,
            bottomNavBarTabTypes = MainBottomNavBarTabType.entries,
            currentBottomNavBarTab = MainBottomNavBarTabType.HOME,
            onBottomNavBarTabSelected = {}
        )
    }
}
