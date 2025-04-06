package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.domain.repository.UserRepository

class GetMyProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<UserInfo> = userRepository.getMyProfile()
}
