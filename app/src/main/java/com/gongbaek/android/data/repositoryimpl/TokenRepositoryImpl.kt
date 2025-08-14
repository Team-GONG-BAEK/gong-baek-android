package com.gongbaek.android.data.repositoryimpl

import com.gongbaek.android.data.local.datasource.TokenLocalDataSource
import com.gongbaek.android.data.mapper.todomain.toDomain
import com.gongbaek.android.data.remote.datasource.TokenReissueRemoteDataSource
import com.gongbaek.android.data.remote.util.HttpResponseHandler.handleApiResponse
import com.gongbaek.android.data.remote.util.safeApiCall
import com.gongbaek.android.data.security.AuthTokens
import com.gongbaek.android.domain.model.UserAuth
import com.gongbaek.android.domain.repository.TokenRepository
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

    override suspend fun reissueToken(): Result<UserAuth> = safeApiCall {
        tokenRemoteDataSource.reissueToken("Bearer ${tokenLocalDataSource.getRefreshToken()}")
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun clearAuthTokens() = tokenLocalDataSource.clearAuthTokens()
}
