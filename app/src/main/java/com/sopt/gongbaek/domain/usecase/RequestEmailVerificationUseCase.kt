package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.AuthRepository

class RequestEmailVerificationUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, schoolName: String) =
        repository.requestEmailVerification(email = email, schoolName = schoolName)
}
