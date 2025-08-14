package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.GroupRoom
import com.gongbaek.android.domain.repository.CommentRepository
import com.gongbaek.android.domain.repository.GroupRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoadGroupRoomScreenUseCase(
    private val groupRepository: GroupRepository,
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<GroupRoom> =
        coroutineScope {
            try {
                val groupInfoDeferred = async { groupRepository.getGroupDetail(groupId = groupId, groupType = groupType) }
                val groupMembersDeferred = async { groupRepository.getGroupMembers(groupId = groupId, groupType = groupType) }
                val groupCommentsDeferred = async {
                    commentRepository.getGroupComments(
                        isPublic = false,
                        groupId = groupId,
                        groupType = groupType
                    )
                }

                val groupInfoResult = groupInfoDeferred.await()
                val groupMembersResult = groupMembersDeferred.await()
                val groupCommentsResult = groupCommentsDeferred.await()

                if (groupInfoResult.isSuccess && groupMembersResult.isSuccess && groupCommentsResult.isSuccess) {
                    Result.success(
                        GroupRoom(
                            groupInfo = groupInfoResult.getOrThrow(),
                            groupMembers = groupMembersResult.getOrThrow(),
                            groupComments = groupCommentsResult.getOrThrow()
                        )
                    )
                } else {
                    Result.failure(
                        Exception(
                            "Failed to load group detail: " +
                                "\nGroupInfo=${groupInfoResult.exceptionOrNull()?.message}" +
                                "\nGroupMembers=${groupMembersResult.exceptionOrNull()?.message}" +
                                "\nGroupComments=${groupCommentsResult.exceptionOrNull()?.message}"
                        )
                    )
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
