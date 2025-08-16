package com.gongbaek.android.data.remote.datasource

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.LoginResponseDto

interface TokenReissueRemoteDataSource {
    suspend fun reissueToken(refreshToken: String): ApiResponse<LoginResponseDto>
}
