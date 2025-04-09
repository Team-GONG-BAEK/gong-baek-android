package com.sopt.gongbaek.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpInfoRequestDto(
    @SerialName("platform")
    val platform: String,
    @SerialName("profileImg")
    val profileImage: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("mbti")
    val mbti: String,
    @SerialName("email")
    val email: String,
    @SerialName("schoolName")
    val university: String,
    @SerialName("schoolMajor")
    val major: String,
    @SerialName("enterYear")
    val enterYear: Int,
    @SerialName("introduction")
    val introduction: String,
    @SerialName("sex")
    val gender: String,
    @SerialName("timeTable")
    val timeTable: List<RegisterTimeTableRequestDto>
)
