package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.GroupInfo
import com.gongbaek.android.domain.repository.GroupRepository

class GetMyGroupsUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(category: String, status: Boolean): Result<List<GroupInfo>> =
        groupRepository.getMyGroups(category = category, status = status)
}
