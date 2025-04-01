package com.sopt.gongbaek.data.remote.datasource

import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.response.MyProfileResponseDto

interface UserRemoteDataSource {
    suspend fun getMyProfile(): ApiResponse<MyProfileResponseDto>
}