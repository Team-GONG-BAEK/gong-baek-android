package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.UserProfileResponseDto
import com.gongbaek.android.domain.model.UserProfile

fun UserProfileResponseDto.toDomain(): UserProfile =
    UserProfile(
        nickname = nickname,
        schoolName = schoolName
    )
