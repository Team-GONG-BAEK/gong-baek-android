package com.sopt.gongbaek.presentation.ui.auth.state

data class AcademicInfoState(
    val university: String = "",
    val major: String = "",
    val enterYear: Int = DEFAULT_ENTER_YEAR,
    val universitySearchQuery: String = "",
    val searchedUniversities: List<String> = emptyList(),
    val majorSearchQuery: String = "",
    val searchedMajors: List<String> = emptyList()
) {
    val isAcademicInfoComplete: Boolean
        get() = isUniversitySearchComplete && isMajorSearchComplete && enterYear != DEFAULT_ENTER_YEAR

    val isUniversitySearchComplete: Boolean
        get() = university.isNotEmpty()

    val isMajorSearchComplete: Boolean
        get() = major.isNotEmpty()

    companion object {
        private const val DEFAULT_ENTER_YEAR = 0
    }
}
