package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.repository.GroupRepository

class ReportGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int, groupType: String): Result<String> =
        groupRepository.reportGroup(groupId, groupType)
}
