package com.sopt.gongbaek.data.local.datasource

import com.sopt.gongbaek.data.security.AuthTokens

interface TokenLocalDataSource {
    suspend fun saveAuthTokens(tokens: AuthTokens)
    suspend fun getSignUpToken(): String
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun clearAuthTokens()
}
