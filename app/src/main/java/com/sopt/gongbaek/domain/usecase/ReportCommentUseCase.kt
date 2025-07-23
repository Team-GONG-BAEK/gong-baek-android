package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.CommentRepository

class ReportCommentUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(commentId: Int): Result<String> =
        commentRepository.reportComment(commentId)
}
