package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.AuthRepository

class WithdrawUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> =
        authRepository.withdraw()
}
