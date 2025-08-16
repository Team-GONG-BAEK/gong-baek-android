package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.AuthRepository

class WithdrawUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> =
        authRepository.withdraw()
}
