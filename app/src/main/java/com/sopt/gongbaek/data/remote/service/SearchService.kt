package com.sopt.gongbaek.data.remote.service

import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.response.SearchMajorsResponseDto
import com.sopt.gongbaek.data.remote.dto.response.SearchUniversitiesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/api/v1/school/search")
    suspend fun searchUniversities(
        @Query("schoolName") universityName: String
    ): ApiResponse<SearchUniversitiesResponseDto>

    @GET("/api/v1/school/major/search")
    suspend fun searchMajors(
        @Query("schoolName") universityName: String,
        @Query("majorName") majorName: String
    ): ApiResponse<SearchMajorsResponseDto>
}
