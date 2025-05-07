package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.CommentRepository

class DeleteCommentUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(commentId: Int): Result<Unit> =
        commentRepository.deleteComment(commentId = commentId)
}
