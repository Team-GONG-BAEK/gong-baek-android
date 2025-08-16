package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.GroupRepository

class FetchLatestGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupType: String) = groupRepository.getLatestGroup(groupType)
}
