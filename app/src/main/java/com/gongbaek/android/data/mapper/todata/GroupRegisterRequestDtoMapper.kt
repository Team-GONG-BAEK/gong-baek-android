package com.gongbaek.android.data.mapper.todata

import com.gongbaek.android.data.remote.dto.request.GroupRegisterRequestDto
import com.gongbaek.android.domain.model.GroupRegisterInfo

fun GroupRegisterInfo.toData(): GroupRegisterRequestDto =
    GroupRegisterRequestDto(
        groupType = groupType,
        weekDate = weekDate,
        weekDay = weekDay,
        startTime = startTime,
        endTime = endTime,
        category = category,
        coverImg = coverImg,
        location = location,
        maxPeopleCount = maxPeopleCount,
        groupTitle = groupTitle,
        introduction = introduction
    )
