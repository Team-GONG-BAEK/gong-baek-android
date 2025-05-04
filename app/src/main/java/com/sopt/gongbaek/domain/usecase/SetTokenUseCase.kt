package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.TokenRepository

class SetTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) =
        tokenRepository.saveAuthTokens("", accessToken, refreshToken)
}
