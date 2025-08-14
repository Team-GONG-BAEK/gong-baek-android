package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.SearchMajorsResponseDto
import com.gongbaek.android.domain.model.Majors

fun SearchMajorsResponseDto.toDomain() =
    Majors(
        majors = this.majors
    )
