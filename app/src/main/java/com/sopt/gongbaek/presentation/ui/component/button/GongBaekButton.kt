package com.sopt.gongbaek.presentation.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun GongBaekButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: GongBaekButtonColors = GongBaekButtonDefault.gongBaekButtonColors(),
    shape: Shape = GongBaekButtonDefault.shape,
    contentPadding: PaddingValues = GongBaekButtonDefault.ContentPadding,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val containerColor: Color = colors.containerColor(enabled)
    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            .background(
                color = containerColor,
                shape = shape
            )
            .clickableWithoutRipple(
                enabled = enabled,
                onClick = onClick
            )
            .padding(contentPadding)
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Immutable
data class GongBaekButtonColors(
    val containerColor: Color,
    val disabledContainerColor: Color
) {
    @Stable
    fun containerColor(enabled: Boolean): Color = if (enabled) containerColor else disabledContainerColor
}

object GongBaekButtonDefault {
    private val ButtonVerticalPadding = 16.dp
    private val ButtonHorizontalPadding = 0.dp

    val ContentPadding =
        PaddingValues(
            vertical = ButtonVerticalPadding,
            horizontal = ButtonHorizontalPadding
        )

    private val ButtonShape = 6.dp
    val shape: Shape @Composable get() = RoundedCornerShape(ButtonShape)

    @Composable
    fun gongBaekButtonColors(
        containerColor: Color = GongBaekTheme.colors.mainOrange,
        disabledContainerColor: Color = GongBaekTheme.colors.gray03
    ): GongBaekButtonColors = GongBaekButtonColors(
        containerColor = containerColor,
        disabledContainerColor = disabledContainerColor
    )
}


@PreviewFontScale
@Preview
@Composable
private fun GongBaekButtonPreview() {
    GongBaekButton(
        onClick = {},
        colors = GongBaekButtonDefault.gongBaekButtonColors(
            containerColor = GongBaekTheme.colors.black,
        ),
        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 12.dp),
    ) {
        Text(
            text = "코드받기",
            color = GongBaekTheme.colors.white,
            style = GongBaekTheme.typography.body1.m16,
        )
    }
}
