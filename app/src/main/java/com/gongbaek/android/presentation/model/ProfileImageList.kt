package com.gongbaek.android.presentation.model

import com.gongbaek.android.R

object ProfileImageList {
    private val profileImageList = listOf(
        R.drawable.img_detail_profile_0,
        R.drawable.img_detail_profile_1,
        R.drawable.img_detail_profile_2,
        R.drawable.img_detail_profile_3,
        R.drawable.img_detail_profile_4,
        R.drawable.img_detail_profile_5
    )

    fun getProfileImage(index: Int) =
        profileImageList.getOrElse(index) { R.drawable.img_detail_profile_0 }
}
