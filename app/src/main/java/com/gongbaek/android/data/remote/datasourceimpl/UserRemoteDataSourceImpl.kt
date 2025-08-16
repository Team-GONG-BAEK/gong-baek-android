package com.gongbaek.android.data.remote.datasourceimpl

import com.gongbaek.android.data.remote.datasource.UserRemoteDataSource
import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.MyProfileResponseDto
import com.gongbaek.android.data.remote.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {

    override suspend fun getMyProfile(): ApiResponse<MyProfileResponseDto> =
        userService.getMyProfile()
}
