package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.GroupRepository

class FetchHomeScreenUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke() = groupRepository.getNearestGroup()
}
