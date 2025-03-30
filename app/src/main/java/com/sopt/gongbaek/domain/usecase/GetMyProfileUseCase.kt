package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.domain.repository.AuthRepository

class GetMyProfileUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<UserInfo> = authRepository.getMyProfile()
}
