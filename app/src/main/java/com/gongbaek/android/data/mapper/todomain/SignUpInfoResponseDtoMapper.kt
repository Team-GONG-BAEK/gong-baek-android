package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.SignUpInfoResponseDto
import com.gongbaek.android.domain.model.UserAuth

fun SignUpInfoResponseDto.toDomain() = UserAuth(
    userId = this.userId,
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)
