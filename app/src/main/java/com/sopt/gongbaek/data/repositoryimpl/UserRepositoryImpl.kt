package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.UserRemoteDataSource
import com.sopt.gongbaek.data.remote.util.handleApiResponse
import com.sopt.gongbaek.domain.model.UserInfo
import com.sopt.gongbaek.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getMyProfile(): Result<UserInfo> =
        runCatching {
            userRemoteDataSource.getMyProfile()
                .handleApiResponse()
                .getOrThrow()
                .toDomain()
        }
}