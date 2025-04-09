package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.SignUpInfo
import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.repository.AuthRepository

class RequestSignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(signUpInfo: SignUpInfo): Result<UserAuth> =
        authRepository.signUp(signUpInfo = signUpInfo)
}
