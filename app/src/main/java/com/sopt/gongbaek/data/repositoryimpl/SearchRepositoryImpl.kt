package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.SearchRemoteDataSource
import com.sopt.gongbaek.data.remote.util.handleApiResponse
import com.sopt.gongbaek.domain.model.Majors
import com.sopt.gongbaek.domain.model.Universities
import com.sopt.gongbaek.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchUniversities(universityName: String): Result<Universities> =
        runCatching {
            searchRemoteDataSource.searchUniversities(universityName = universityName)
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun searchMajors(universityName: String, majorName: String): Result<Majors> =
        runCatching {
            searchRemoteDataSource.searchMajors(universityName = universityName, majorName = majorName)
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }
}
