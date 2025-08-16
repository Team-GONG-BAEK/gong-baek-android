package com.gongbaek.android.data.mapper.todata

import com.gongbaek.android.data.remote.dto.request.RegisterTimeTableRequestDto
import com.gongbaek.android.domain.model.TimeTable

fun TimeTable.toData() = RegisterTimeTableRequestDto(
    weekDay = this.weekDay,
    startTime = this.startTime,
    endTime = this.endTime
)
