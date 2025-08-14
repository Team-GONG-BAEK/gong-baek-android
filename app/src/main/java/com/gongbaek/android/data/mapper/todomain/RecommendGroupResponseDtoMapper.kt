package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.RecommendGroupInfoResponseDto
import com.gongbaek.android.domain.model.RecommendGroupInfo

fun RecommendGroupInfoResponseDto.toDomain() =
    RecommendGroupInfo(
        groupId = groupId,
        category = category,
        coverImg = coverImg,
        profileImg = profileImg,
        nickname = nickname,
        groupType = groupType,
        groupTitle = groupTitle,
        weekDay = weekDay ?: "",
        weekDate = weekDate ?: "",
        startTime = startTime,
        endTime = endTime,
        location = location
    )
