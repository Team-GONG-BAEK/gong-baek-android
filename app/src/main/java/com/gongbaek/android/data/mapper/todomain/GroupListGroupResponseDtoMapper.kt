package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.GroupListGroupResponseDto
import com.gongbaek.android.domain.model.GroupInfo

fun GroupListGroupResponseDto.toDomain(): GroupInfo =
    GroupInfo(
        groupId = groupId,
        coverImg = coverImg,
        status = "RECRUITING",
        category = category,
        cycle = groupType,
        title = groupTitle,
        date = weekDate,
        dayOfWeek = weekDay,
        startTime = startTime,
        endTime = endTime,
        place = location
    )
