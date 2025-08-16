package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.GroupCommentsResponseDto
import com.gongbaek.android.domain.model.GroupComments

fun GroupCommentsResponseDto.toDomain() = GroupComments(
    groupId = groupId,
    groupStatus = groupStatus,
    groupCycle = groupType,
    commentCount = commentCount,
    groupCommentList = comments.map { comment ->
        GroupComments.GroupComment(
            commentId = comment.commentId,
            commentWriter = comment.nickname,
            commentContent = comment.body,
            createdAt = comment.createdAt,
            isGroupHost = comment.isGroupHost,
            isWriter = comment.isWriter
        )
    }
)
