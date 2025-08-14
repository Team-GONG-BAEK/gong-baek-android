package com.gongbaek.android.data.remote.datasourceimpl

import com.gongbaek.android.data.remote.datasource.CommentRemoteDataSource
import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.base.NullableApiResponse
import com.gongbaek.android.data.remote.dto.request.DeleteCommentRequestDto
import com.gongbaek.android.data.remote.dto.request.PostCommentRequestDto
import com.gongbaek.android.data.remote.dto.response.GroupCommentsResponseDto
import com.gongbaek.android.data.remote.dto.response.PostCommentResponseDto
import com.gongbaek.android.data.remote.service.CommentService
import javax.inject.Inject

class CommentRemoteDataSourceImpl @Inject constructor(
    private val commentService: CommentService
) : CommentRemoteDataSource {

    override suspend fun getGroupComments(
        isPublic: Boolean,
        groupId: Int,
        groupType: String
    ): ApiResponse<GroupCommentsResponseDto> =
        commentService.getGroupComments(
            isPublic = isPublic,
            groupId = groupId,
            groupType = groupType
        )

    override suspend fun postComment(postCommentRequestDto: PostCommentRequestDto): NullableApiResponse<PostCommentResponseDto> =
        commentService.postComment(postCommentRequestDto = postCommentRequestDto)

    override suspend fun deleteComment(deleteCommentRequestDto: DeleteCommentRequestDto): NullableApiResponse<Unit> =
        commentService.deleteComment(deleteCommentRequestDto = deleteCommentRequestDto)

    override suspend fun reportComment(commentId: Long): NullableApiResponse<String> =
        commentService.reportComment(commentId = commentId)
}
