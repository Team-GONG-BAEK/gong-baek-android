package com.gongbaek.android.data.remote.service

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.LoginResponseDto
import retrofit2.http.Header
import retrofit2.http.PATCH

interface TokenReissueService {

    @PATCH("/api/v1/reissue/token")
    suspend fun reissueToken(
        @Header("Authorization") refreshToken: String
    ): ApiResponse<LoginResponseDto>
}
