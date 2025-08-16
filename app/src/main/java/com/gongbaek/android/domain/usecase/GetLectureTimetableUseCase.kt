package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.LectureTimetableRepository

class GetLectureTimetableUseCase(
    private val lectureTimetableRepository: LectureTimetableRepository
) {
    operator fun invoke(): Map<String, List<Int>> = lectureTimetableRepository.getTimetable()
}
