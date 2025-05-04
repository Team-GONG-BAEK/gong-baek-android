package com.sopt.gongbaek.data.remote.datasource

import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.response.LoginResponseDto

interface TokenReissueRemoteDataSource {
    suspend fun reissueToken(refreshToken: String): ApiResponse<LoginResponseDto>
}
