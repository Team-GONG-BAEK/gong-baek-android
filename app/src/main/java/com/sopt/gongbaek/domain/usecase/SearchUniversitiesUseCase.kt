package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.Universities
import com.sopt.gongbaek.domain.repository.SearchRepository

class SearchUniversitiesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(universityName: String): Result<Universities> =
        searchRepository.searchUniversities(universityName)
}
