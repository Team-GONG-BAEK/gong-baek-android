package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.UserInfo
import com.gongbaek.android.domain.repository.UserRepository

class GetMyProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<UserInfo> = userRepository.getMyProfile()
}
