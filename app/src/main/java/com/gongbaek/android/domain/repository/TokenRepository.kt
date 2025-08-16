package com.gongbaek.android.domain.repository

import com.gongbaek.android.domain.model.UserAuth

interface TokenRepository {
    suspend fun saveAuthTokens(signUpToken: String, accessToken: String, refreshToken: String)
    suspend fun getSignUpToken(): String
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun reissueToken(): Result<UserAuth>
    suspend fun clearAuthTokens()
}
