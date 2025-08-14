package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.UserAuth
import com.gongbaek.android.domain.repository.TokenRepository

class ReissueTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): Result<UserAuth> =
        tokenRepository.reissueToken()
}
