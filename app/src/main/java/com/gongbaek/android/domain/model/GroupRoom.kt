package com.gongbaek.android.domain.model

data class GroupRoom(
    val groupInfo: GroupInfo = GroupInfo(),
    val groupMembers: GroupMembers = GroupMembers(),
    val groupComments: GroupComments = GroupComments()
)
