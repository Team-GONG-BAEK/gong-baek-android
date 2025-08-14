package com.gongbaek.android.data.remote.service

import com.gongbaek.android.data.remote.dto.base.ApiResponse
import com.gongbaek.android.data.remote.dto.base.NullableApiResponse
import com.gongbaek.android.data.remote.dto.request.DeleteCommentRequestDto
import com.gongbaek.android.data.remote.dto.request.PostCommentRequestDto
import com.gongbaek.android.data.remote.dto.response.GroupCommentsResponseDto
import com.gongbaek.android.data.remote.dto.response.PostCommentResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {

    @GET("/api/v1/comments")
    suspend fun getGroupComments(
        @Query("isPublic") isPublic: Boolean,
        @Query("groupId") groupId: Int,
        @Query("groupType") groupType: String
    ): ApiResponse<GroupCommentsResponseDto>

    @POST("/api/v1/comment")
    suspend fun postComment(
        @Body postCommentRequestDto: PostCommentRequestDto
    ): NullableApiResponse<PostCommentResponseDto>

    @HTTP(method = "DELETE", path = "/api/v1/comment", hasBody = true)
    suspend fun deleteComment(
        @Body deleteCommentRequestDto: DeleteCommentRequestDto
    ): NullableApiResponse<Unit>

    @POST("/api/v1/reports/comment/{commentId}")
    suspend fun reportComment(
        @Path("commentId") commentId: Long
    ): NullableApiResponse<String>
}
