package com.gongbaek.android.data.repositoryimpl

import com.gongbaek.android.data.mapper.todomain.toDomain
import com.gongbaek.android.data.remote.datasource.SearchRemoteDataSource
import com.gongbaek.android.data.remote.util.HttpResponseHandler.handleApiResponse
import com.gongbaek.android.data.remote.util.safeApiCall
import com.gongbaek.android.domain.model.Majors
import com.gongbaek.android.domain.model.Universities
import com.gongbaek.android.domain.repository.SearchRepository
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
