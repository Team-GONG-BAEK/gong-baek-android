package com.sopt.gongbaek.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteCommentRequestDto(
    @SerialName("commentId") val commentId: Int
)
