package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.CommentRepository

class DeleteCommentUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(commentId: Int): Result<Unit> =
        commentRepository.deleteComment(commentId = commentId)
}
