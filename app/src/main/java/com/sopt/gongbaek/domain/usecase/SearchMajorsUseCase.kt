package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.Majors
import com.sopt.gongbaek.domain.repository.SearchRepository

class SearchMajorsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(universityName: String, majorName: String): Result<Majors> =
        searchRepository.searchMajors(universityName, majorName)
            .map { majors ->
                Majors(majors.majors.distinct())
            }
}
