package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.Majors
import com.gongbaek.android.domain.repository.SearchRepository

class SearchMajorsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(universityName: String, majorName: String): Result<Majors> =
        searchRepository.searchMajors(universityName, majorName)
            .map { majors ->
                Majors(majors.majors.distinct())
            }
}
