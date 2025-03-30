package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(kakaoToken: String, platform: String): Result<UserAuth> =
        authRepository.login(kakaoToken, platform)
}
