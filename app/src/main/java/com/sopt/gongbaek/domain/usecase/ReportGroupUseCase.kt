package com.sopt.gongbaek.domain.usecase

import com.sopt.gongbaek.domain.repository.GroupRepository

class ReportGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<Unit> =
        groupRepository.reportGroup(groupId, groupType)
}
