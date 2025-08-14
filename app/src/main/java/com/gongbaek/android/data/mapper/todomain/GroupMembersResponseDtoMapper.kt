package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.GroupMembersResponseDto
import com.gongbaek.android.domain.model.GroupMembers

fun GroupMembersResponseDto.toDomain() = GroupMembers(
    maxPeopleCount = maxPeopleCount,
    currentPeopleCount = currentPeopleCount,
    members = members.map { member ->
        GroupMembers.Member(
            profileImg = member.profileImg,
            nickname = member.nickname,
            isHost = member.isHost
        )
    }
)
