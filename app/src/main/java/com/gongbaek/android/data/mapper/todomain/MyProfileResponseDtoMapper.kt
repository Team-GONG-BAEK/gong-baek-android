package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.MyProfileResponseDto
import com.gongbaek.android.domain.model.UserInfo

fun MyProfileResponseDto.toDomain(): UserInfo =
    UserInfo(
        profileImage = profileImg,
        nickname = nickname,
        school = schoolName,
        major = majorName
    )
