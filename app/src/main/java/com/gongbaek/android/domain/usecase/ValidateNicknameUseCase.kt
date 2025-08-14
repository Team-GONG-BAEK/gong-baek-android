package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.AuthRepository

class ValidateNicknameUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(nickname: String) = authRepository.validateNickname(nickname)
}
