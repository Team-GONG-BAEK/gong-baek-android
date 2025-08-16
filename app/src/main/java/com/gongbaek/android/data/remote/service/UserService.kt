package com.gongbaek.android.data.remote.service

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.MyProfileResponseDto
import retrofit2.http.GET

interface UserService {

    @GET("/api/v1/user/my/profile")
    suspend fun getMyProfile(): ApiResponse<MyProfileResponseDto>
}
