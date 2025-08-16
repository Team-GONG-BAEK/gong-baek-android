package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.GroupHostResponseDto
import com.gongbaek.android.domain.model.GroupHost

fun GroupHostResponseDto.toDomain() = GroupHost(
    profileImg = profileImg,
    nickname = nickname,
    gender = sex,
    major = schoolMajor,
    enterYear = enterYear,
    mbti = mbti,
    introduction = introduction
)
