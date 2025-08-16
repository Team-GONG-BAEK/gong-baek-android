package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.TokenRepository

class SetTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) =
        tokenRepository.saveAuthTokens("", accessToken, refreshToken)
}
