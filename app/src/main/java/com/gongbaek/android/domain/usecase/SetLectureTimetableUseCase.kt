package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.LectureTimetableRepository

class SetLectureTimetableUseCase(
    private val lectureTimetableRepository: LectureTimetableRepository
) {
    operator fun invoke(lectureTimetable: Map<String, List<Int>>) = lectureTimetableRepository.setTimetable(lectureTimetable)
}
