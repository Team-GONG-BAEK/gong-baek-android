package com.sopt.gongbaek.presentation.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun GongBaekTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = GongBaekTheme.colors.gray01,
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) GongBaekTheme.colors.black else Color.Transparent,
                shape = RoundedCornerShape(6.dp)
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .wrapContentHeight()
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp
            ),
        textStyle = GongBaekTheme.typography.body1.m16.copy(
            color = GongBaekTheme.colors.gray10
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(GongBaekTheme.colors.gray05)
    ) { innerTextField ->
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = "입력해보던가",
                    style = GongBaekTheme.typography.body1.m16,
                    color = GongBaekTheme.colors.gray04,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            innerTextField()
        }
    }
}

@PreviewFontScale
@Preview(showBackground = true)
@Composable
private fun GongBaekTextFieldPreview() {
    var value by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        GongBaekTextField(
            value = value,
            onValueChanged = { newValue -> value = newValue }
        )
    }
}
