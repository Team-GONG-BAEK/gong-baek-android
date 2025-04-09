package com.sopt.gongbaek.data.mapper.todata

import com.sopt.gongbaek.data.remote.dto.request.SignUpInfoRequestDto
import com.sopt.gongbaek.domain.model.SignUpInfo

fun SignUpInfo.toData() = SignUpInfoRequestDto(
    platform = this.platform,
    university = this.university,
    major = this.major,
    enterYear = this.enterYear,
    email = this.email,
    nickname = this.nickname,
    gender = this.gender,
    profileImage = this.profileImage,
    mbti = this.mbti,
    introduction = this.introduction,
    timeTable = this.timeTable.map { it.toData() }
)
