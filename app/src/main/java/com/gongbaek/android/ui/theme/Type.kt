package com.gongbaek.android.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.gongbaek.android.R

private val PretendardFont = FontFamily(
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium),
    Font(R.font.pretendard_regular, weight = FontWeight.Normal)
)

private val PretendardLineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None
)

sealed interface TypographyTokens {
    @Immutable
    data class Head1(
        val b30: TextStyle
    )

    @Immutable
    data class Head2(
        val b24: TextStyle,
        val m24: TextStyle,
        val r24: TextStyle
    )

    @Immutable
    data class Title1(
        val b20: TextStyle,
        val m20: TextStyle,
        val r20: TextStyle
    )

    @Immutable
    data class Title2(
        val b18: TextStyle,
        val sb18: TextStyle,
        val m18: TextStyle,
        val r18: TextStyle
    )

    @Immutable
    data class Body1(
        val b16: TextStyle,
        val sb16: TextStyle,
        val m16: TextStyle,
        val r16: TextStyle
    )

    @Immutable
    data class Body2(
        val b14: TextStyle,
        val sb14: TextStyle,
        val m14: TextStyle,
        val r14: TextStyle
    )

    @Immutable
    data class Caption1(
        val sb13: TextStyle,
        val m13: TextStyle,
        val r13: TextStyle
    )

    @Immutable
    data class Caption2(
        val b12: TextStyle,
        val m12: TextStyle,
        val r12: TextStyle
    )
}

@Immutable
data class GongBaekTypography(
    val head1: TypographyTokens.Head1,
    val head2: TypographyTokens.Head2,
    val title1: TypographyTokens.Title1,
    val title2: TypographyTokens.Title2,
    val body1: TypographyTokens.Body1,
    val body2: TypographyTokens.Body2,
    val caption1: TypographyTokens.Caption1,
    val caption2: TypographyTokens.Caption2
)

val defaultGongBaekTypography = GongBaekTypography(
    head1 = TypographyTokens.Head1(
        b30 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 30.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    head2 = TypographyTokens.Head2(
        b24 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 26.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m24 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 26.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r24 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 26.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    title1 = TypographyTokens.Title1(
        b20 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m20 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r20 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = (-1).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    title2 = TypographyTokens.Title2(
        b18 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        sb18 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m18 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r18 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    body1 = TypographyTokens.Body1(
        b16 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        sb16 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m16 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r16 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    body2 = TypographyTokens.Body2(
        b14 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        sb14 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m14 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r14 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    caption1 = TypographyTokens.Caption1(
        sb13 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m13 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r13 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    ),
    caption2 = TypographyTokens.Caption2(
        b12 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        m12 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        ),
        r12 = TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.5).sp,
            lineHeightStyle = PretendardLineHeightStyle
        )
    )
)

val LocalGongBaekTypography = staticCompositionLocalOf { defaultGongBaekTypography }
