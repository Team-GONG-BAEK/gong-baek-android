package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.CommentRepository

class ReportCommentUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(commentId: Int): Result<String> =
        commentRepository.reportComment(commentId)
}
