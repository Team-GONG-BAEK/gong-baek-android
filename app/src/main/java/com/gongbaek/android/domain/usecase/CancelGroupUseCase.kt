package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.GroupRepository

class CancelGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<Unit> =
        groupRepository.cancelGroup(groupId = groupId, groupType = groupType)
}
