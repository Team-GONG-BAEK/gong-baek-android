package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.SearchRemoteDataSource
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleApiResponse
import com.sopt.gongbaek.data.remote.util.safeApiCall
import com.sopt.gongbaek.domain.model.Majors
import com.sopt.gongbaek.domain.model.Universities
import com.sopt.gongbaek.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchUniversities(universityName: String): Result<Universities> = safeApiCall {
        searchRemoteDataSource.searchUniversities(universityName = universityName)
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun searchMajors(universityName: String, majorName: String): Result<Majors> = safeApiCall {
        searchRemoteDataSource.searchMajors(universityName = universityName, majorName = majorName)
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }
}
