package com.gongbaek.android.data.mapper.todomain

import com.gongbaek.android.data.remote.dto.response.SearchUniversitiesResponseDto
import com.gongbaek.android.domain.model.Universities

fun SearchUniversitiesResponseDto.toDomain() =
    Universities(
        universities = this.universities
    )
