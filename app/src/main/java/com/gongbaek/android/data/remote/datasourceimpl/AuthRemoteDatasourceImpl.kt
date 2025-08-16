package com.gongbaek.android.data.remote.datasourceimpl

import com.gongbaek.android.data.remote.datasource.AuthRemoteDataSource
import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.base.NullableApiResponse
import com.gongbaek.android.data.remote.dto.request.LoginRequestDto
import com.gongbaek.android.data.remote.dto.request.SignUpInfoRequestDto
import com.gongbaek.android.data.remote.dto.response.LoginResponseDto
import com.gongbaek.android.data.remote.dto.response.SignUpInfoResponseDto
import com.gongbaek.android.data.remote.dto.response.UserProfileResponseDto
import com.gongbaek.android.data.remote.dto.response.UserTimeTableResponseDto
import com.gongbaek.android.data.remote.service.AuthService
import javax.inject.Inject

class AuthRemoteDatasourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun login(kakaoToken: String, loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseDto> =
        authService.login(kakaoToken, loginRequestDto)

    override suspend fun signUp(signUpToken: String, signUpInfoRequestDto: SignUpInfoRequestDto): ApiResponse<SignUpInfoResponseDto> =
        authService.signup(signUpToken, signUpInfoRequestDto)

    override suspend fun validateNickname(nickname: String): NullableApiResponse<Unit> =
        authService.validateNickname(nickname)

    override suspend fun getUserProfile(): ApiResponse<UserProfileResponseDto> =
        authService.getUserProfile()

    override suspend fun getUserLectureTimeTable(): ApiResponse<UserTimeTableResponseDto> =
        authService.getUserLectureTimeTable()

    override suspend fun logout(): NullableApiResponse<SignUpInfoResponseDto> =
        authService.logout()

    override suspend fun withdraw(): NullableApiResponse<Unit> =
        authService.withdraw()

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
