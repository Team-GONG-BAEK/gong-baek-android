package com.sopt.gongbaek.data.remote.datasource

import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.base.NullableApiResponse
import com.sopt.gongbaek.data.remote.dto.request.LoginRequestDto
import com.sopt.gongbaek.data.remote.dto.request.SignUpInfoRequestDto
import com.sopt.gongbaek.data.remote.dto.response.LoginResponseDto
import com.sopt.gongbaek.data.remote.dto.response.SignUpInfoResponseDto
import com.sopt.gongbaek.data.remote.dto.response.UserProfileResponseDto
import com.sopt.gongbaek.data.remote.dto.response.UserTimeTableResponseDto

interface AuthRemoteDataSource {
    suspend fun login(kakaoToken: String, loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseDto>
    suspend fun signUp(signUpInfoRequestDto: SignUpInfoRequestDto): ApiResponse<SignUpInfoResponseDto>
    suspend fun validateNickname(nickname: String): NullableApiResponse<Unit>
    suspend fun getUserProfile(): ApiResponse<UserProfileResponseDto>
    suspend fun getUserLectureTimeTable(): ApiResponse<UserTimeTableResponseDto>
    suspend fun reissueToken(refreshToken: String): ApiResponse<LoginResponseDto>
    suspend fun logout(): ApiResponse<Unit>
    suspend fun requestEmailVerification(email: String, schoolName: String): NullableApiResponse<Unit>
    suspend fun verifyEmailCode(
        email: String,
        schoolName: String,
        code: String
    ): NullableApiResponse<Unit>
}
