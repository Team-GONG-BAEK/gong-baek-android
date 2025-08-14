package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.NearestGroupResponseDto
import com.gongbaek.android.domain.model.NearestGroup

fun NearestGroupResponseDto.toDomain() = NearestGroup(
    groupId = groupId,
    category = category,
    groupType = groupType,
    groupTitle = groupTitle,
    weekDay = weekDay ?: "",
    weekDate = weekDate ?: "",
    currentPeopleCount = currentPeopleCount,
    maxPeopleCount = maxPeopleCount,
    startTime = startTime,
    endTime = endTime,
    location = location
)
