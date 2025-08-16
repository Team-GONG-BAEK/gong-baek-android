package com.gongbaek.android.data.remote.datasourceimpl

import com.gongbaek.android.data.remote.datasource.SearchRemoteDataSource
import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.SearchMajorsResponseDto
import com.gongbaek.android.data.remote.dto.response.SearchUniversitiesResponseDto
import com.gongbaek.android.data.remote.service.SearchService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRemoteDataSource {

    override suspend fun searchUniversities(universityName: String): ApiResponse<SearchUniversitiesResponseDto> =
        searchService.searchUniversities(universityName)

    override suspend fun searchMajors(universityName: String, majorName: String): ApiResponse<SearchMajorsResponseDto> =
        searchService.searchMajors(universityName, majorName)
}
