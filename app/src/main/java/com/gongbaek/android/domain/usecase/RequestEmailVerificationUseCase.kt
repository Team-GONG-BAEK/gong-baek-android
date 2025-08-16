package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.AuthRepository

class RequestEmailVerificationUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, schoolName: String) =
        repository.requestEmailVerification(email = email, schoolName = schoolName)
}
