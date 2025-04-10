package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.AuthRepository

class VerifyEmailCodeUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        schoolName: String,
        code: String
    ) = repository.verifyEmailCode(
        email = email,
        schoolName = schoolName,
        code = code
    )
}
