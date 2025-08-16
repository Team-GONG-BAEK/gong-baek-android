package com.gongbaek.android.domain.usecase

import com.gongbaek.android.domain.model.GroupInfo
import com.gongbaek.android.domain.repository.GroupRepository

class GetGroupsUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(category: String?): Result<List<GroupInfo>> =
        groupRepository.getGroups(category = category)
}
