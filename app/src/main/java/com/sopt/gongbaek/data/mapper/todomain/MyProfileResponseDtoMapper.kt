package com.sopt.gongbaek.data.mapper.todomain

import com.sopt.gongbaek.data.remote.dto.response.MyProfileResponseDto
import com.sopt.gongbaek.domain.model.UserInfo

fun MyProfileResponseDto.toDomain(): UserInfo =
    UserInfo(
        profileImage = profileImg,
        nickname = nickname,
        school = schoolName,
        major = majorName
    )
