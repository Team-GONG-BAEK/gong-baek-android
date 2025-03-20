package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {

    }
}