package com.gongbaek.android.data.mapper.todata

import com.gongbaek.android.data.remote.dto.request.PostCommentRequestDto
import com.gongbaek.android.domain.model.Comment

fun Comment.toData() = PostCommentRequestDto(
    groupId = groupId,
    groupType = cycle,
    isPublic = isPublic,
    body = content
)
