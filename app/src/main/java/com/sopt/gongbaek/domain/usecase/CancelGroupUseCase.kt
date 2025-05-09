package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.GroupRepository

class CancelGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<Unit> =
        groupRepository.cancelGroup(groupId = groupId, groupType = groupType)
}
