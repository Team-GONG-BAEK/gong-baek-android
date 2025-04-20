package com.sopt.gongbaek.data.mapper.todomain

import com.sopt.gongbaek.data.remote.dto.response.GroupHostResponseDto
import com.sopt.gongbaek.domain.model.GroupHost

fun GroupHostResponseDto.toDomain() = GroupHost(
    profileImg = profileImg,
    nickname = nickname,
    gender = sex,
    major = schoolMajor,
    enterYear = enterYear,
    mbti = mbti,
    introduction = introduction
)
