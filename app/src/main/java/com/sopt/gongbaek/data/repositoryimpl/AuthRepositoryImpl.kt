package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.local.datasource.TokenLocalDataSource
import com.sopt.gongbaek.data.mapper.todata.toData
import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.AuthRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.request.LoginRequestDto
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleApiResponse
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleNullableApiResponse
import com.sopt.gongbaek.data.remote.util.safeApiCall
import com.sopt.gongbaek.domain.model.SignUpInfo
import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.model.UserProfile
import com.sopt.gongbaek.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDatasource: AuthRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : AuthRepository {

    override suspend fun login(kakaoToken: String, platform: String): Result<UserAuth> = safeApiCall {
        authRemoteDatasource.login(kakaoToken = kakaoToken, loginRequestDto = LoginRequestDto(platform = platform))
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun signUp(signUpInfo: SignUpInfo): Result<UserAuth> = safeApiCall {
        authRemoteDatasource.signUp(signUpToken = "Bearer ${tokenLocalDataSource.getSignUpToken()}", signUpInfoRequestDto = signUpInfo.toData())
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun validateNickname(nickname: String): Result<Unit> = safeApiCall {
        authRemoteDatasource.validateNickname(nickname = nickname)
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun getUserProfile(): Result<UserProfile> = safeApiCall {
        authRemoteDatasource.getUserProfile()
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getUserLectureTimeTable() = safeApiCall {
        authRemoteDatasource.getUserLectureTimeTable()
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun logout(): Result<UserAuth> = safeApiCall {
        authRemoteDatasource.logout()
            .handleNullableApiResponse()
            .getOrThrow()
            ?.toDomain() ?: UserAuth(null, "", "")
    }

    override suspend fun withdraw(): Result<Unit> = safeApiCall {
        authRemoteDatasource.withdraw()
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun requestEmailVerification(email: String, schoolName: String): Result<Unit> = safeApiCall {
        authRemoteDatasource.requestEmailVerification(email = email, schoolName = schoolName)
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun verifyEmailCode(
        email: String,
        schoolName: String,
        code: String
    ): Result<Unit> = safeApiCall {
        authRemoteDatasource.verifyEmailCode(
            email = email,
            schoolName = schoolName,
            code = code
        ).handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }
}
