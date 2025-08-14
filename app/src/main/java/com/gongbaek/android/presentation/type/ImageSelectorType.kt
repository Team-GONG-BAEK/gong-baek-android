package com.gongbaek.android.presentation.type

import androidx.annotation.DrawableRes
import com.gongbaek.android.R
import com.gongbaek.android.domain.type.GroupCategoryType

enum class ImageSelectorType(
    @DrawableRes val imageButtonResIdList: List<Int>,
    val chunkedCount: Int
) {
    Profile(
        imageButtonResIdList = listOf(
            R.drawable.img_btn_profile_0,
            R.drawable.img_btn_profile_1,
            R.drawable.img_btn_profile_2,
            R.drawable.img_btn_profile_3,
            R.drawable.img_btn_profile_4,
            R.drawable.img_btn_profile_5
        ),
        chunkedCount = 3
    ),
    Cover(
        imageButtonResIdList = listOf(
            R.drawable.img_study_0,
            R.drawable.img_study_1,
            R.drawable.img_study_2,
            R.drawable.img_study_3,
            R.drawable.img_study_4,
            R.drawable.img_study_5,
            R.drawable.img_dining_0,
            R.drawable.img_dining_1,
            R.drawable.img_dining_2,
            R.drawable.img_dining_3,
            R.drawable.img_dining_4,
            R.drawable.img_dining_5,
            R.drawable.img_exercise_0,
            R.drawable.img_exercise_1,
            R.drawable.img_exercise_2,
            R.drawable.img_exercise_3,
            R.drawable.img_exercise_4,
            R.drawable.img_exercise_5,
            R.drawable.img_playing_0,
            R.drawable.img_playing_1,
            R.drawable.img_playing_2,
            R.drawable.img_playing_3,
            R.drawable.img_playing_4,
            R.drawable.img_playing_5,
            R.drawable.img_networking_0,
            R.drawable.img_networking_1,
            R.drawable.img_networking_2,
            R.drawable.img_networking_3,
            R.drawable.img_networking_4,
            R.drawable.img_networking_5,
            R.drawable.img_others_0,
            R.drawable.img_others_1,
            R.drawable.img_others_2,
            R.drawable.img_others_3,
            R.drawable.img_others_4,
            R.drawable.img_others_5
        ),
        chunkedCount = 2
    );

    companion object {
        private const val IMAGES_PER_CATEGORY = 6

        private val categoryImagesMap = mapOf(
            GroupCategoryType.STUDY to Cover.imageButtonResIdList.subList(0, IMAGES_PER_CATEGORY),
            GroupCategoryType.DINING to Cover.imageButtonResIdList.subList(IMAGES_PER_CATEGORY, IMAGES_PER_CATEGORY * 2),
            GroupCategoryType.EXERCISE to Cover.imageButtonResIdList.subList(IMAGES_PER_CATEGORY * 2, IMAGES_PER_CATEGORY * 3),
            GroupCategoryType.PLAYING to Cover.imageButtonResIdList.subList(IMAGES_PER_CATEGORY * 3, IMAGES_PER_CATEGORY * 4),
            GroupCategoryType.NETWORKING to Cover.imageButtonResIdList.subList(IMAGES_PER_CATEGORY * 4, IMAGES_PER_CATEGORY * 5),
            GroupCategoryType.OTHERS to Cover.imageButtonResIdList.subList(IMAGES_PER_CATEGORY * 5, IMAGES_PER_CATEGORY * 6)
        )

        fun getImageListFromCategory(category: String): List<Int> {
            val categoryType = GroupCategoryType.entries.find { it.name == category } ?: GroupCategoryType.STUDY
            return categoryImagesMap[categoryType] ?: Cover.imageButtonResIdList.subList(0, IMAGES_PER_CATEGORY)
        }

        fun getCoverImage(category: String, imageIndex: Int): Int {
            val imageList = getImageListFromCategory(category)
            return imageList.getOrElse(imageIndex) { R.drawable.img_study_0 }
        }
    }
}
