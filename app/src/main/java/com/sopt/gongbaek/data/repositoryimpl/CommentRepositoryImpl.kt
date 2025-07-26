package com.sopt.gongbaek.data.repositoryimpl

import com.sopt.gongbaek.data.mapper.todata.toData
import com.sopt.gongbaek.data.mapper.todomain.toDomain
import com.sopt.gongbaek.data.remote.datasource.CommentRemoteDataSource
import com.sopt.gongbaek.data.remote.dto.request.DeleteCommentRequestDto
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleApiResponse
import com.sopt.gongbaek.data.remote.util.HttpResponseHandler.handleNullableApiResponse
import com.sopt.gongbaek.data.remote.util.safeApiCall
import com.sopt.gongbaek.domain.model.Comment
import com.sopt.gongbaek.domain.model.GroupComments
import com.sopt.gongbaek.domain.repository.CommentRepository
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
