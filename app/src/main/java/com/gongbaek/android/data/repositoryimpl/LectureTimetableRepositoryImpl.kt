package com.gongbaek.android.data.repositoryimpl

import com.gongbaek.android.data.local.datasource.LectureTimetableLocalDataSource
import com.gongbaek.android.domain.repository.LectureTimetableRepository
import javax.inject.Inject

class LectureTimetableRepositoryImpl @Inject constructor(
    private val lectureTimetableLocalDataSource: LectureTimetableLocalDataSource
) : LectureTimetableRepository {

    override fun getTimetable(): Map<String, List<Int>> = lectureTimetableLocalDataSource.getTimetable()

    override fun setTimetable(lectureTimetable: Map<String, List<Int>>) = lectureTimetableLocalDataSource.setTimetable(lectureTimetable)
}
