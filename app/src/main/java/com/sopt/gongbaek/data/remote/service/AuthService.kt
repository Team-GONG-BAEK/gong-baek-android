package com.sopt.gongbaek.data.remote.service

import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.base.NullableApiResponse
import com.sopt.gongbaek.data.remote.dto.request.LoginRequestDto
import com.sopt.gongbaek.data.remote.dto.request.RegisterUserInfoRequestDto
import com.sopt.gongbaek.data.remote.dto.response.LoginResponseDto
import com.sopt.gongbaek.data.remote.dto.response.RegisterUserInfoResponseDto
import com.sopt.gongbaek.data.remote.dto.response.UserProfileResponseDto
import com.sopt.gongbaek.data.remote.dto.response.UserTimeTableResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("/api/v1/login")
    suspend fun login(
        @Header("Authorization") kakaoToken: String,
        @Body loginRequestDto: LoginRequestDto
    ): ApiResponse<LoginResponseDto>

    @POST("/api/v1/user/signup")
    suspend fun requestUserInfo(
        @Body registerUserInfoRequestDto: RegisterUserInfoRequestDto
    ): ApiResponse<RegisterUserInfoResponseDto>

    @POST("/api/v1/user/validate/nickname")
    suspend fun validateNickname(
        @Query("nickname") nickname: String
    ): NullableApiResponse<Unit>

    @GET("/api/v1/user/home/profile")
    suspend fun getUserProfile(): ApiResponse<UserProfileResponseDto>

    @GET("/api/v1/my/timeTable")
    suspend fun getUserLectureTimeTable(): ApiResponse<UserTimeTableResponseDto>

    @PATCH("/api/v1/reissue/token")
    suspend fun reissueToken(
        @Header("Authorization") refreshToken: String
    ): ApiResponse<LoginResponseDto>

    @DELETE("/api/v1/logout")
    suspend fun logout(): ApiResponse<Unit>

    @POST("/api/v1/emails/verification-requests")
    suspend fun requestEmailVerification(
        @Query("email") email: String,
        @Query("schoolName") schoolName: String
    ): NullableApiResponse<Unit>
}
