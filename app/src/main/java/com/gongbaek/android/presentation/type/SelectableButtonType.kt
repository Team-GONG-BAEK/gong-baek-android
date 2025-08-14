package com.gongbaek.android.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.gongbaek.android.R
import com.gongbaek.android.domain.type.DayOfWeekType
import com.gongbaek.android.domain.type.GenderType
import com.gongbaek.android.domain.type.GroupCategoryType
import com.gongbaek.android.domain.type.GroupCycleType
import com.gongbaek.android.domain.type.MbtiFirstLetterType
import com.gongbaek.android.domain.type.MbtiFourthLetterType
import com.gongbaek.android.domain.type.MbtiSecondLetterType
import com.gongbaek.android.domain.type.MbtiThirdLetterType
import com.gongbaek.android.ui.theme.defaultGongBaekColors
import com.gongbaek.android.ui.theme.defaultGongBaekTypography

enum class SelectableButtonType(
    val options: List<String>,
    val chunkedCount: Int = 2,
    val selectedTypoStyle: TextStyle = defaultGongBaekTypography.body1.m16,
    @ColorRes val selectedButtonColor: Color = defaultGongBaekColors.subOrange,
    @ColorRes val selectedTextColor: Color = defaultGongBaekColors.mainOrange,
    @ColorRes val unSelectedButtonColor: Color = defaultGongBaekColors.gray01,
    @ColorRes val unSelectedTextColor: Color = defaultGongBaekColors.gray07,
    @DrawableRes val categoryImgResIds: List<Int> = emptyList()
) {
    MBTI_FIRST(
        options = listOf(
            MbtiFirstLetterType.E.description,
            MbtiFirstLetterType.I.description
        )
    ),
    MBTI_SECOND(
        options = listOf(
            MbtiSecondLetterType.S.description,
            MbtiSecondLetterType.N.description
        )
    ),
    MBTI_THIRD(
        options = listOf(
            MbtiThirdLetterType.T.description,
            MbtiThirdLetterType.F.description
        )
    ),
    MBTI_FOURTH(
        options = listOf(
            MbtiFourthLetterType.J.description,
            MbtiFourthLetterType.P.description
        )
    ),
    GENDER(
        options = listOf(
            GenderType.MAN.description,
            GenderType.WOMAN.description
        )
    ),
    GROUP_CYCLE(
        options = listOf(
            GroupCycleType.ONCE.description,
            GroupCycleType.WEEKLY.description
        )
    ),
    DAY_OF_WEEK(
        options = listOf(
            DayOfWeekType.MON.description,
            DayOfWeekType.TUE.description,
            DayOfWeekType.WED.description,
            DayOfWeekType.THU.description,
            DayOfWeekType.FRI.description
        ),
        selectedTypoStyle = defaultGongBaekTypography.body1.b16,
        chunkedCount = 1
    ),
    CATEGORY(
        options = listOf(
            GroupCategoryType.STUDY.description,
            GroupCategoryType.DINING.description,
            GroupCategoryType.EXERCISE.description,
            GroupCategoryType.NETWORKING.description,
            GroupCategoryType.PLAYING.description,
            GroupCategoryType.OTHERS.description
        ),
        selectedTextColor = defaultGongBaekColors.gray08,
        unSelectedTextColor = defaultGongBaekColors.gray08,
        categoryImgResIds = listOf(
            R.drawable.img_category_study,
            R.drawable.img_category_dining,
            R.drawable.img_category_exercise,
            R.drawable.img_category_networking,
            R.drawable.img_category_playing,
            R.drawable.img_category_others
        )
    );
}
