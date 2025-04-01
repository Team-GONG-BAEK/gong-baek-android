package com.sopt.gongbaek.domain.repository

import com.sopt.gongbaek.domain.model.UserInfo

interface UserRepository {
    suspend fun getMyProfile(): Result<UserInfo>
}