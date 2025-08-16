package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.GroupDetailResponseDto
import com.gongbaek.android.domain.model.GroupInfo

fun GroupDetailResponseDto.toDomain() = GroupInfo(
    groupId = groupId,
    coverImg = coverImg,
    status = status,
    category = category,
    cycle = groupType,
    title = groupTitle,
    date = weekDate,
    dayOfWeek = weekDay,
    startTime = startTime,
    endTime = endTime,
    place = location,
    introduction = introduction,
    maxPeopleCount = maxPeopleCount,
    currentPeopleCount = currentPeopleCount,
    isHost = isHost,
    isApply = isApply
)
