package com.sopt.gongbaek.data.remote.datasourceimpl

import com.sopt.gongbaek.data.remote.datasource.UserRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.response.MyProfileResponseDto
import com.sopt.gongbaek.data.remote.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {

    override suspend fun getMyProfile(): ApiResponse<MyProfileResponseDto> =
        userService.getMyProfile()
}
