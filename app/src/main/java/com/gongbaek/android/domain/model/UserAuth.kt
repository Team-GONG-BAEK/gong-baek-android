package com.gongbaek.android.domain.model

data class UserAuth(
    val userId: Long?,
    val accessToken: String,
    val refreshToken: String
)
