package com.sopt.gongbaek.data.remote.service

import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.response.MyProfileResponseDto
import retrofit2.http.GET

interface UserService {

    @GET("/api/v1/user/my/profile")
    suspend fun getMyProfile(): ApiResponse<MyProfileResponseDto>
}
