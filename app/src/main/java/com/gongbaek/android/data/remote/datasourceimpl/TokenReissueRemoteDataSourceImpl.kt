package com.gongbaek.android.data.remote.datasourceimpl

import com.gongbaek.android.data.remote.datasource.TokenReissueRemoteDataSource
import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.LoginResponseDto
import com.gongbaek.android.data.remote.service.TokenReissueService
import javax.inject.Inject

class TokenReissueRemoteDataSourceImpl @Inject constructor(
    private val tokenReissueService: TokenReissueService
) : TokenReissueRemoteDataSource {
    override suspend fun reissueToken(refreshToken: String): ApiResponse<LoginResponseDto> =
        tokenReissueService.reissueToken(refreshToken)
}
