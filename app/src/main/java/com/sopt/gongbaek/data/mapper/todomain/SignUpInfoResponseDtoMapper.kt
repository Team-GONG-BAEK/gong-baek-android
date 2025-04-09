package com.sopt.gongbaek.data.mapper.todomain

import com.sopt.gongbaek.data.remote.dto.response.SignUpInfoResponseDto
import com.sopt.gongbaek.domain.model.UserAuth

fun SignUpInfoResponseDto.toDomain() = UserAuth(
    userId = this.userId,
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)
