package com.sopt.gongbaek.domain.type

enum class MbtiFourthLetterType(
    val description: String
) {
    P(description = "P"),
    J(description = "J");

    companion object {
        private const val INVALID_DESCRIPTION_ERROR_MESSAGE = "유효하지 않은 MBTI 타입입니다: "

        fun fromDescription(description: String): MbtiFourthLetterType =
            requireNotNull(entries.find { it.description == description }) {
                INVALID_DESCRIPTION_ERROR_MESSAGE + description
            }
    }
}
