package com.sopt.gongbaek.domain.type

enum class GenderType(
    val description: String
) {
    MAN(description = "남자"),
    WOMAN(description = "여자");

    companion object {
        private const val INVALID_NAME_ERROR_MESSAGE = "유효하지 않은 성별 이름입니다: "
        private const val INVALID_DESCRIPTION_ERROR_MESSAGE = "유효하지 않은 성별 설명입니다: "

        fun fromName(name: String): GenderType =
            requireNotNull(entries.find { it.name == name }) {
                INVALID_NAME_ERROR_MESSAGE + name
            }

        fun fromDescription(description: String): GenderType =
            requireNotNull(entries.find { it.description == description }) {
                INVALID_DESCRIPTION_ERROR_MESSAGE + description
            }
    }
}
