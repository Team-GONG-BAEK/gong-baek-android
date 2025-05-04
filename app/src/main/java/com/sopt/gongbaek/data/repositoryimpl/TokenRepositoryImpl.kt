package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.local.datasource.TokenLocalDataSource
import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.TokenReissueRemoteDataSource
import com.sopt.gongbaek.data.remote.util.handleApiResponse
import com.sopt.gongbaek.data.security.AuthTokens
import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenRemoteDataSource: TokenReissueRemoteDataSource
) : TokenRepository {
    override suspend fun saveAuthTokens(signUpToken: String, accessToken: String, refreshToken: String) =
        tokenLocalDataSource.saveAuthTokens(AuthTokens(signUpToken, accessToken, refreshToken))

    override suspend fun getSignUpToken(): String = tokenLocalDataSource.getSignUpToken()

    override suspend fun getAccessToken(): String = tokenLocalDataSource.getAccessToken()

    override suspend fun getRefreshToken(): String = tokenLocalDataSource.getRefreshToken()

    override suspend fun reissueToken(): Result<UserAuth> =
        runCatching {
            tokenRemoteDataSource.reissueToken("Bearer ${tokenLocalDataSource.getRefreshToken()}")
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun clearAuthTokens() = tokenLocalDataSource.clearAuthTokens()
}
