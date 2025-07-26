package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.mapper.todata.toData
import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.GroupRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.request.ApplyGroupRequestDto
import com.sopt.gongbaek.data.remote.dto.request.GroupManagementRequestDto
import com.sopt.gongbaek.data.remote.dto.response.GroupRegisterResponseDto
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleApiResponse
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleNullableApiResponse
import com.sopt.gongbaek.data.remote.util.safeApiCall
import com.sopt.gongbaek.domain.model.GroupHost
import com.sopt.gongbaek.domain.model.GroupInfo
import com.sopt.gongbaek.domain.model.GroupMembers
import com.sopt.gongbaek.domain.model.GroupRegisterInfo
import com.sopt.gongbaek.domain.model.NearestGroup
import com.sopt.gongbaek.domain.model.RecommendGroupInfo
import com.sopt.gongbaek.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupRemoteDataSource
) : GroupRepository {

    override suspend fun getMyGroups(category: String, status: Boolean): Result<List<GroupInfo>> = safeApiCall {
        groupDataSource.getMyGroups(category = category, status = status)
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getGroupDetail(groupId: Int, groupType: String): Result<GroupInfo> = safeApiCall {
        groupDataSource.getGroupDetail(groupId = groupId, groupType = groupType)
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getGroupHost(groupId: Int, groupType: String): Result<GroupHost> = safeApiCall {
        groupDataSource.getGroupHost(groupId = groupId, groupType = groupType)
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun applyGroup(groupId: Int, groupType: String): Result<Unit> = safeApiCall {
        groupDataSource.applyGroup(ApplyGroupRequestDto(groupId = groupId, groupType = groupType))
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun getGroups(category: String?): Result<List<GroupInfo>> = safeApiCall {
        groupDataSource.getGroups(category = category)
            .handleApiResponse()
            .getOrThrow()
            .map { it.toDomain() }
    }

    override suspend fun getNearestGroup(): Result<NearestGroup?> = safeApiCall {
        groupDataSource.getNearestGroup()
            .handleNullableApiResponse()
            .getOrThrow()
            ?.toDomain()
    }

    override suspend fun getLatestGroup(groupType: String): Result<List<RecommendGroupInfo>> = safeApiCall {
        groupDataSource.getLatestGroup(groupType = groupType)
            .handleApiResponse()
            .getOrThrow()
            .map { it.toDomain() }
    }

    override suspend fun postGroup(groupRegisterInfo: GroupRegisterInfo): Result<GroupRegisterResponseDto> = safeApiCall {
        groupDataSource.postGroup(groupRegisterRequestDto = groupRegisterInfo.toData())
            .handleApiResponse()
            .getOrThrow()
    }

    override suspend fun getGroupMembers(groupId: Int, groupType: String): Result<GroupMembers> = safeApiCall {
        groupDataSource.getGroupMembers(groupId = groupId, groupType = groupType)
            .handleApiResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun deleteGroup(groupId: Int, groupType: String): Result<Unit> = safeApiCall {
        groupDataSource.deleteGroup(GroupManagementRequestDto(groupId, groupType))
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun cancelGroup(groupId: Int, groupType: String): Result<Unit> = safeApiCall {
        groupDataSource.cancelGroup(GroupManagementRequestDto(groupId, groupType))
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun reportGroup(groupId: Int, groupType: String): Result<String> = safeApiCall {
        groupDataSource.reportGroup(groupId.toLong(), groupType)
            .handleNullableApiResponse()
            .getOrThrow() ?: ""
    }
}
