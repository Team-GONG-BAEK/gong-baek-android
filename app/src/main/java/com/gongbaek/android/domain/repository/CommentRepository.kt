package com.gongbaek.android.domain.repository

import com.gongbaek.android.domain.model.Comment
import com.gongbaek.android.domain.model.GroupComments

interface CommentRepository {
    suspend fun getGroupComments(
        isPublic: Boolean,
        groupId: Int,
        groupType: String
    ): Result<GroupComments>
    suspend fun postComment(comment: Comment): Result<Int>
    suspend fun deleteComment(commentId: Int): Result<Unit>
    suspend fun reportComment(commentId: Int): Result<String>
}
