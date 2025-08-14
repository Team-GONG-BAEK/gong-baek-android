package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.UserTimeTableResponseDto
import com.gongbaek.android.domain.model.UserLectureTimeTable

fun UserTimeTableResponseDto.toDomain(): List<UserLectureTimeTable> =
    timeTable.map { lectureTimeTable ->
        UserLectureTimeTable(
            idx = lectureTimeTable.idx,
            weekDay = lectureTimeTable.weekDay,
            startTime = lectureTimeTable.startTime,
            endTime = lectureTimeTable.endTime
        )
    }
