package com.sopt.gongbaek.domain.model

data class SignUpInfo(
    val platform: String,
    val university: String,
    val enterYear: Int,
    val major: String,
    val email: String,
    val nickname: String,
    val gender: String,
    val profileImage: Int,
    val mbti: String,
    val introduction: String,
    val timeTable: List<TimeTable>
)
