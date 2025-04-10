package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.mapper.todata.toData
import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.AuthRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.request.LoginRequestDto
import com.sopt.gongbaek.data.remote.util.handleApiResponse
import com.sopt.gongbaek.data.remote.util.handleNullableApiResponse
import com.sopt.gongbaek.domain.model.SignUpInfo
import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.model.UserProfile
import com.sopt.gongbaek.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDatasource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(kakaoToken: String, platform: String): Result<UserAuth> =
        runCatching {
            authRemoteDatasource.login(kakaoToken = kakaoToken, loginRequestDto = LoginRequestDto(platform = platform))
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun signUp(signUpInfo: SignUpInfo): Result<UserAuth> =
        runCatching {
            authRemoteDatasource.signUp(signUpInfoRequestDto = signUpInfo.toData())
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun validateNickname(nickname: String): Result<Unit> =
        runCatching {
            authRemoteDatasource.validateNickname(nickname = nickname)
                .handleNullableApiResponse()
                .exceptionOrNull()
        }

    override suspend fun getUserProfile(): Result<UserProfile> =
        runCatching {
            authRemoteDatasource.getUserProfile()
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun getUserLectureTimeTable() =
        runCatching {
            authRemoteDatasource.getUserLectureTimeTable()
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun reissueToken(refreshToken: String): Result<UserAuth> =
        runCatching {
            authRemoteDatasource.reissueToken(refreshToken)
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun logout(): Result<Unit> =
        runCatching {
            authRemoteDatasource.logout()
                .handleApiResponse()
                .getOrThrow()
        }

    override suspend fun requestEmailVerification(email: String, schoolName: String): Result<Unit> =
        runCatching {
            authRemoteDatasource.requestEmailVerification(email = email, schoolName = schoolName)
                .handleNullableApiResponse()
                .getOrThrow()
        }

    override suspend fun verifyEmailCode(
        email: String,
        schoolName: String,
        code: String
    ): Result<Unit> =
        runCatching {
            authRemoteDatasource.verifyEmailCode(
                email = email,
                schoolName = schoolName,
                code = code
            )
                .handleNullableApiResponse()
                .getOrThrow()
        }
}
