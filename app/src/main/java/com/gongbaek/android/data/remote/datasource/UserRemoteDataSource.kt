package com.gongbaek.android.data.remote.datasource

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.MyProfileResponseDto

interface UserRemoteDataSource {
    suspend fun getMyProfile(): ApiResponse<MyProfileResponseDto>
}
