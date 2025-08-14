package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.AuthRepository

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
