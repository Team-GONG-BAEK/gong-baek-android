package com.gongbaek.android.data.remote.datasourceimpl

import com.gongbaek.android.data.remote.datasource.GroupRemoteDataSource
import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.base.NullableApiResponse
import com.gongbaek.android.data.remote.dto.request.ApplyGroupRequestDto
import com.gongbaek.android.data.remote.dto.request.GroupManagementRequestDto
import com.gongbaek.android.data.remote.dto.request.GroupRegisterRequestDto
import com.gongbaek.android.data.remote.dto.response.GroupDetailResponseDto
import com.gongbaek.android.data.remote.dto.response.GroupHostResponseDto
import com.gongbaek.android.data.remote.dto.response.GroupListGroupResponseDto
import com.gongbaek.android.data.remote.dto.response.GroupMembersResponseDto
import com.gongbaek.android.data.remote.dto.response.GroupRegisterResponseDto
import com.gongbaek.android.data.remote.dto.response.MyGroupsResponseDto
import com.gongbaek.android.data.remote.dto.response.NearestGroupResponseDto
import com.gongbaek.android.data.remote.dto.response.RecommendGroupInfoResponseDto
import com.gongbaek.android.data.remote.service.GroupService
import javax.inject.Inject

class GroupRemoteDataSourceImpl @Inject constructor(
    private val groupService: GroupService
) : GroupRemoteDataSource {

    override suspend fun getMyGroups(category: String, status: Boolean): ApiResponse<MyGroupsResponseDto> =
        groupService.getMyGroups(category = category, status = status)

    override suspend fun getGroupDetail(groupId: Int, groupType: String): ApiResponse<GroupDetailResponseDto> =
        groupService.getGroupDetail(groupId = groupId, groupType = groupType)

    override suspend fun getGroupHost(groupId: Int, groupType: String): ApiResponse<GroupHostResponseDto> =
        groupService.getGroupHost(groupId = groupId, groupType = groupType)

    override suspend fun applyGroup(applyGroupRequestDto: ApplyGroupRequestDto): NullableApiResponse<Unit> =
        groupService.applyGroup(applyGroupRequestDto = applyGroupRequestDto)

    override suspend fun getGroups(category: String?): ApiResponse<List<GroupListGroupResponseDto>> =
        groupService.getGroups(category = category)

    override suspend fun getNearestGroup(): NullableApiResponse<NearestGroupResponseDto> =
        groupService.getNearestGroup()

    override suspend fun getLatestGroup(groupType: String): ApiResponse<List<RecommendGroupInfoResponseDto>> =
        groupService.getLatestGroup(groupType = groupType)

    override suspend fun postGroup(groupRegisterRequestDto: GroupRegisterRequestDto): ApiResponse<GroupRegisterResponseDto> =
        groupService.postGroup(groupRegisterRequestDto = groupRegisterRequestDto)

    override suspend fun getGroupMembers(groupId: Int, groupType: String): ApiResponse<GroupMembersResponseDto> =
        groupService.getGroupMembers(groupId = groupId, groupType = groupType)

    override suspend fun deleteGroup(groupManagementRequestDto: GroupManagementRequestDto): NullableApiResponse<Unit> =
        groupService.deleteGroup(groupManagementRequestDto = groupManagementRequestDto)

    override suspend fun cancelGroup(groupManagementRequestDto: GroupManagementRequestDto): NullableApiResponse<Unit> =
        groupService.cancelGroup(groupManagementRequestDto = groupManagementRequestDto)

    override suspend fun reportGroup(groupId: Long, groupType: String): NullableApiResponse<String> =
        groupService.reportGroup(groupId = groupId, groupType = groupType)
}
