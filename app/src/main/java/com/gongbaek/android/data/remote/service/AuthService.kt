package com.gongbaek.android.data.remote.service

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.base.NullableApiResponse
import com.gongbaek.android.data.remote.dto.request.LoginRequestDto
import com.gongbaek.android.data.remote.dto.request.SignUpInfoRequestDto
import com.gongbaek.android.data.remote.dto.response.LoginResponseDto
import com.gongbaek.android.data.remote.dto.response.SignUpInfoResponseDto
import com.gongbaek.android.data.remote.dto.response.UserProfileResponseDto
import com.gongbaek.android.data.remote.dto.response.UserTimeTableResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("/api/v1/login")
    suspend fun login(
        @Header("Authorization") kakaoToken: String,
        @Body loginRequestDto: LoginRequestDto
    ): ApiResponse<LoginResponseDto>

    @POST("/api/v1/user/signup")
    suspend fun signup(
        @Header("Authorization") signUpToken: String,
        @Body signUpInfoRequestDto: SignUpInfoRequestDto
    ): ApiResponse<SignUpInfoResponseDto>

    @POST("/api/v1/user/validate/nickname")
    suspend fun validateNickname(
        @Query("nickname") nickname: String
    ): NullableApiResponse<Unit>

    @GET("/api/v1/user/home/profile")
    suspend fun getUserProfile(): ApiResponse<UserProfileResponseDto>

    @GET("/api/v1/my/timeTable")
    suspend fun getUserLectureTimeTable(): ApiResponse<UserTimeTableResponseDto>

    @DELETE("/api/v1/logout")
    suspend fun logout(): NullableApiResponse<SignUpInfoResponseDto>

    @DELETE("/api/v1/withdraw")
    suspend fun withdraw(): NullableApiResponse<Unit>

    @POST("/api/v1/emails/verification-requests")
    suspend fun requestEmailVerification(
        @Query("email") email: String,
        @Query("schoolName") schoolName: String
    ): NullableApiResponse<Unit>

    @GET("/api/v1/emails/verifications")
    suspend fun verifyEmailCode(
        @Query("email") email: String,
        @Query("schoolName") schoolName: String,
        @Query("code") code: String
    ): NullableApiResponse<Unit>
}
