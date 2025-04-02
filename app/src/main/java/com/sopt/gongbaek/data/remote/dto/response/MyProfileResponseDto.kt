package com.sopt.gongbaek.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyProfileResponseDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("schoolName")
    val schoolName: String,
    @SerialName("majorName")
    val majorName: String,
    @SerialName("profileImg")
    val profileImg: Int
)
