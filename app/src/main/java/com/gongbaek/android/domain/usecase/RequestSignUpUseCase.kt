package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.SignUpInfo
import com.gongbaek.android.domain.model.UserAuth
import com.gongbaek.android.domain.repository.AuthRepository

class RequestSignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(signUpInfo: SignUpInfo): Result<UserAuth> =
        authRepository.signUp(signUpInfo = signUpInfo)
}
