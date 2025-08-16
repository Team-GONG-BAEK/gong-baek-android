package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.LoginResponseDto
import com.gongbaek.android.domain.model.UserAuth

fun LoginResponseDto.toDomain() = UserAuth(
    userId = userId,
    accessToken = accessToken,
    refreshToken = refreshToken
)
