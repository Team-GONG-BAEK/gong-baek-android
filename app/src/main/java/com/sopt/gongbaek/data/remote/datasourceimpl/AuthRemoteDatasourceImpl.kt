package com.sopt.gongbaek.data.remote.datasourceimpl

import com.sopt.gongbaek.data.remote.datasource.AuthRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.base.NullableApiResponse
import com.sopt.gongbaek.data.remote.dto.request.LoginRequestDto
import com.sopt.gongbaek.data.remote.dto.request.RegisterUserInfoRequestDto
import com.sopt.gongbaek.data.remote.dto.response.LoginResponseDto
import com.sopt.gongbaek.data.remote.dto.response.RegisterUserInfoResponseDto
import com.sopt.gongbaek.data.remote.dto.response.UserProfileResponseDto
import com.sopt.gongbaek.data.remote.dto.response.UserTimeTableResponseDto
import com.sopt.gongbaek.data.remote.service.AuthService
import javax.inject.Inject

class AuthRemoteDatasourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun login(kakaoToken: String, loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseDto> =
        authService.login(kakaoToken, loginRequestDto)

    override suspend fun registerUserInfo(registerUserInfoRequestDto: RegisterUserInfoRequestDto): ApiResponse<RegisterUserInfoResponseDto> =
        authService.requestUserInfo(registerUserInfoRequestDto)

    override suspend fun validateNickname(nickname: String): NullableApiResponse<Unit> =
        authService.validateNickname(nickname)

    override suspend fun getUserProfile(): ApiResponse<UserProfileResponseDto> =
        authService.getUserProfile()

    override suspend fun getUserLectureTimeTable(): ApiResponse<UserTimeTableResponseDto> =
        authService.getUserLectureTimeTable()

    override suspend fun reissueToken(refreshToken: String): ApiResponse<LoginResponseDto> =
        authService.reissueToken(refreshToken)

    override suspend fun logout(): ApiResponse<Unit> =
        authService.logout()

    override suspend fun requestEmailVerification(email: String, schoolName: String): NullableApiResponse<Unit> =
        authService.requestEmailVerification(email = email, schoolName = schoolName)

    override suspend fun verifyEmailCode(
        email: String,
        schoolName: String,
        code: String
    ): NullableApiResponse<Unit> =
        authService.verifyEmailCode(
            email = email,
            schoolName = schoolName,
            code = code
        )
}
