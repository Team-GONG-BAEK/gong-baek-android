package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.GroupRepository

class ApplyGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<Unit> =
        groupRepository.applyGroup(groupId = groupId, groupType = groupType)
}
