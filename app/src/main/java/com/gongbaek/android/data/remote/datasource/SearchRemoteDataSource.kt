package com.gongbaek.android.data.remote.datasource

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.response.SearchMajorsResponseDto
import com.gongbaek.android.data.remote.dto.response.SearchUniversitiesResponseDto

interface SearchRemoteDataSource {
    suspend fun searchUniversities(universityName: String): ApiResponse<SearchUniversitiesResponseDto>
    suspend fun searchMajors(universityName: String, majorName: String): ApiResponse<SearchMajorsResponseDto>
}
