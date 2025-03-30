package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.repository.AuthRepository

class ReissueTokenUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: String): Result<UserAuth> =
        authRepository.reissueToken(refreshToken)
}
