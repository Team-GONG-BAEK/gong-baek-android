package com.sopt.gongbaek.domain.type

enum class MbtiSecondLetterType(
    val description: String
) {
    N(description = "N"),
    S(description = "S");

    companion object {
        private const val INVALID_DESCRIPTION_ERROR_MESSAGE = "유효하지 않은 MBTI 타입입니다: "

        fun fromDescription(description: String): MbtiSecondLetterType =
            requireNotNull(entries.find { it.description == description }) {
                INVALID_DESCRIPTION_ERROR_MESSAGE + description
            }
    }
}
