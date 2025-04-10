package com.sopt.gongbaek.domain.type

enum class MbtiThirdLetterType(
    val description: String
) {
    F(description = "F"),
    T(description = "T");

    companion object {
        private const val INVALID_DESCRIPTION_ERROR_MESSAGE = "유효하지 않은 MBTI 타입입니다: "

        fun fromDescription(description: String): MbtiThirdLetterType =
            requireNotNull(entries.find { it.description == description }) {
                INVALID_DESCRIPTION_ERROR_MESSAGE + description
            }
    }
}
