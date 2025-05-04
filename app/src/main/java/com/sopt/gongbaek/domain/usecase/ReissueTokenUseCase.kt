package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.repository.TokenRepository

class ReissueTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): Result<UserAuth> =
        tokenRepository.reissueToken()
}
