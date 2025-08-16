package com.gongbaek.android.domain.usecase

import com.gongbaek.android.data.remote.dto.response.GroupRegisterResponseDto
import com.gongbaek.android.domain.model.GroupRegisterInfo
import com.gongbaek.android.domain.repository.GroupRepository

class PostGroupUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupRegisterInfo: GroupRegisterInfo): Result<GroupRegisterResponseDto> =
        groupRepository.postGroup(groupRegisterInfo = groupRegisterInfo)
}
