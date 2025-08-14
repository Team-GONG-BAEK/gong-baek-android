package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.Universities
import com.gongbaek.android.domain.repository.SearchRepository

class SearchUniversitiesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(universityName: String): Result<Universities> =
        searchRepository.searchUniversities(universityName)
}
