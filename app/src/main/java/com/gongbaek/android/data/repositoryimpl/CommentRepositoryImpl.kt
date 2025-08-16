package com.gongbaek.android.data.repositoryimpl

import com.gongbaek.android.data.mapper.todata.toData
import com.gongbaek.android.data.mapper.todomain.toDomain
import com.gongbaek.android.data.remote.datasource.CommentRemoteDataSource
import com.gongbaek.android.data.remote.dto.request.DeleteCommentRequestDto
import com.gongbaek.android.data.remote.util.HttpResponseHandler.handleApiResponse
import com.gongbaek.android.data.remote.util.HttpResponseHandler.handleNullableApiResponse
import com.gongbaek.android.data.remote.util.safeApiCall
import com.gongbaek.android.domain.model.Comment
import com.gongbaek.android.domain.model.GroupComments
import com.gongbaek.android.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    override suspend fun getGroupComments(
        isPublic: Boolean,
        groupId: Int,
        groupType: String
    ): Result<GroupComments> = safeApiCall {
        commentRemoteDataSource.getGroupComments(
            isPublic = isPublic,
            groupId = groupId,
            groupType = groupType
        ).handleApiResponse().getOrThrow().toDomain()
    }

    override suspend fun postComment(comment: Comment): Result<Int> = safeApiCall {
        commentRemoteDataSource.postComment(comment.toData())
            .handleNullableApiResponse()
            .getOrThrow()
            ?.toDomain() ?: 0
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> = safeApiCall {
        commentRemoteDataSource.deleteComment(DeleteCommentRequestDto(commentId))
            .handleNullableApiResponse()
            .getOrThrow() ?: Unit
    }

    override suspend fun reportComment(commentId: Int): Result<String> = safeApiCall {
        commentRemoteDataSource.reportComment(commentId.toLong())
            .handleNullableApiResponse()
            .getOrThrow() ?: ""
    }
}
