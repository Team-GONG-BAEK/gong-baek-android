package com.sopt.gongbaek.data.mapper.todomain

import com.sopt.gongbaek.data.remote.dto.response.LoginResponseDto
import com.sopt.gongbaek.domain.model.UserAuth

fun LoginResponseDto.toDomain() = UserAuth(
    userId = userId,
    accessToken = accessToken,
    refreshToken = refreshToken
)