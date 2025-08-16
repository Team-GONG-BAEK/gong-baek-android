package com.gongbaek.android.domain.repository

import com.gongbaek.android.domain.model.UserInfo

interface UserRepository {
    suspend fun getMyProfile(): Result<UserInfo>
}
