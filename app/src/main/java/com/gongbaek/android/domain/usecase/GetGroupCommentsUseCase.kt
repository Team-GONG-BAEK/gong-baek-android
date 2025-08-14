package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.GroupComments
import com.gongbaek.android.domain.repository.CommentRepository

class GetGroupCommentsUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(
        isPublic: Boolean,
        groupId: Int,
        groupType: String
    ): Result<GroupComments> =
        commentRepository.getGroupComments(
            isPublic = isPublic,
            groupId = groupId,
            groupType = groupType
        )
}
