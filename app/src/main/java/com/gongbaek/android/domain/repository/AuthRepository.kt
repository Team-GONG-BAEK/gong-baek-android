package com.gongbaek.android.domain.repository

import com.gongbaek.android.domain.model.SignUpInfo
import com.gongbaek.android.domain.model.UserAuth
import com.gongbaek.android.domain.model.UserLectureTimeTable
import com.gongbaek.android.domain.model.UserProfile

interface AuthRepository {
    suspend fun login(kakaoToken: String, platform: String): Result<UserAuth>
    suspend fun signUp(signUpInfo: SignUpInfo): Result<UserAuth>
    suspend fun validateNickname(nickname: String): Result<Unit>
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun getUserLectureTimeTable(): Result<List<UserLectureTimeTable>>
    suspend fun logout(): Result<UserAuth>
    suspend fun withdraw(): Result<Unit>
    suspend fun requestEmailVerification(email: String, schoolName: String): Result<Unit>
    suspend fun verifyEmailCode(
        email: String,
        schoolName: String,
        code: String
    ): Result<Unit>
}
