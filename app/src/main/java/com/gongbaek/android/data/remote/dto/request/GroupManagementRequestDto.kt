package com.gongbaek.android.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupManagementRequestDto(
    @SerialName("groupId") val groupId: Int,
    @SerialName("groupType") val groupType: String
)
