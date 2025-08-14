package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.UserAuth
import com.gongbaek.android.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<UserAuth> =
        authRepository.logout()
}
