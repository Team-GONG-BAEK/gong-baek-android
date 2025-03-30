package com.sopt.gongbaek.domain.repository

import com.sopt.gongbaek.domain.model.UserAuth
import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.domain.model.UserLectureTimeTable
import com.sopt.gongbaek.domain.model.UserProfile

interface AuthRepository {
    suspend fun login(kakaoToken: String, platform: String): Result<UserAuth>
    suspend fun registerUserInfo(userInfo: UserInfo): Result<UserAuth>
    suspend fun validateNickname(nickname: String): Result<Unit>
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun getUserLectureTimeTable(): Result<List<UserLectureTimeTable>>
    suspend fun reissueToken(refreshToken: String): Result<UserAuth>
    suspend fun logout(): Result<Unit>
}
