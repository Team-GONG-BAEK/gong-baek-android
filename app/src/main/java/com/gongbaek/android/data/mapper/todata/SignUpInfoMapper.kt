package com.gongbaek.android.data.mapper.todata

import com.gongbaek.android.data.remote.dto.request.SignUpInfoRequestDto
import com.gongbaek.android.domain.model.SignUpInfo

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
