package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.UserAuth
import com.gongbaek.android.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(kakaoToken: String, platform: String): Result<UserAuth> =
        authRepository.login(kakaoToken, platform)
}
