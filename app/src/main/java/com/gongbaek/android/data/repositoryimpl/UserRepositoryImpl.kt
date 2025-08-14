package com.gongbaek.android.data.repositoryimpl

import com.gongbaek.android.data.mapper.todomain.toDomain
import com.gongbaek.android.data.remote.datasource.UserRemoteDataSource
import com.gongbaek.android.data.remote.util.HttpResponseHandler.handleApiResponse
import com.gongbaek.android.data.remote.util.safeApiCall
import com.gongbaek.android.domain.model.UserInfo
import com.gongbaek.android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getMyProfile(): Result<UserInfo> = safeApiCall {
        userRemoteDataSource.getMyProfile()
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }
}
