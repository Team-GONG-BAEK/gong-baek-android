package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.UserProfile
import com.gongbaek.android.domain.repository.AuthRepository

class FetchUserProfileUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<UserProfile> = authRepository.getUserProfile()
}
