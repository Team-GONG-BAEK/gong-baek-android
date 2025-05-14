package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.model.GroupDetail
import com.sopt.gongbaek.domain.model.GroupHost
import com.sopt.gongbaek.domain.repository.CommentRepository
import com.sopt.gongbaek.domain.repository.GroupRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoadGroupDetailScreenUseCase(
    private val groupRepository: GroupRepository,
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<GroupDetail> =
        coroutineScope {
            try {
                val groupInfoDeferred = async { groupRepository.getGroupDetail(groupId = groupId, groupType = groupType) }
                val groupHostDeferred = async { groupRepository.getGroupHost(groupId = groupId, groupType = groupType) }
                val groupCommentsDeferred = async {
                    commentRepository.getGroupComments(
                        isPublic = true,
                        groupId = groupId,
                        groupType = groupType
                    )
                }

                val groupInfoResult = groupInfoDeferred.await()
                val groupHostResult = groupHostDeferred.await()
                val groupCommentsResult = groupCommentsDeferred.await()

                if (groupInfoResult.isSuccess && groupCommentsResult.isSuccess) {
                    Result.success(
                        GroupDetail(
                            groupInfo = groupInfoResult.getOrThrow(),
                            groupHost = groupHostResult.getOrElse { GroupHost() },
                            groupComments = groupCommentsResult.getOrThrow()
                        )
                    )
                } else {
                    Result.failure(
                        Exception(
                            "Failed to load group detail: " +
                                "\nGroupInfo : ${groupInfoResult.exceptionOrNull()?.message}" +
                                "\nGroupHost : ${groupHostResult.exceptionOrNull()?.message}" +
                                "\nGroupComments : ${groupCommentsResult.exceptionOrNull()?.message}"
                        )
                    )
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
