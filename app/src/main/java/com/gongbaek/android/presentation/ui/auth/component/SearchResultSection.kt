package com.gongbaek.android.presentation.ui.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongbaek.android.R
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun SearchResultSection(
    searchResults: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(searchResults) { searchResultText ->
            SearchResultItem(
                searchResultText = searchResultText,
                isSelected = selectedItem == searchResultText,
                onClick = { onItemSelected(searchResultText) }
            )
        }
    }
}

@Composable
private fun SearchResultItem(
    searchResultText: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) GongBaekTheme.colors.subOrange else GongBaekTheme.colors.white
            )
            .clickableWithoutRipple { onClick() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = searchResultText,
            color = if (isSelected) GongBaekTheme.colors.mainOrange else GongBaekTheme.colors.gray08,
            style = GongBaekTheme.typography.body1.r16,
            modifier = Modifier.padding(vertical = 17.dp)
        )

        if (isSelected) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_check_orange_24),
                contentDescription = null,
                tint = GongBaekTheme.colors.mainOrange
            )
        }
    }
}

@Preview
@Composable
private fun SearchResultSectionPreview() {
    val sampleSearchResults = listOf(
        "건국대학교 서울캠퍼스",
        "고려대학교",
        "연세대학교"
    )
    SearchResultSection(
        searchResults = sampleSearchResults,
        onItemSelected = {},
        selectedItem = ""
    )
}
