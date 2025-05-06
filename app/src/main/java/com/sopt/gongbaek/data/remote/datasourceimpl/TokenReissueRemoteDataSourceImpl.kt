package com.sopt.gongbaek.data.remote.datasourceimpl

import com.sopt.gongbaek.data.remote.datasource.TokenReissueRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.response.LoginResponseDto
import com.sopt.gongbaek.data.remote.service.TokenReissueService
import javax.inject.Inject

class TokenReissueRemoteDataSourceImpl @Inject constructor(
    private val tokenReissueService: TokenReissueService
) : TokenReissueRemoteDataSource {
    override suspend fun reissueToken(refreshToken: String): ApiResponse<LoginResponseDto> =
        tokenReissueService.reissueToken(refreshToken)
}
