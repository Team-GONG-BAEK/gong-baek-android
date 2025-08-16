package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.UserLectureTimeTable
import com.gongbaek.android.domain.repository.AuthRepository

class FetchUserLectureTimetableUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<List<UserLectureTimeTable>> =
        authRepository.getUserLectureTimeTable()
}
