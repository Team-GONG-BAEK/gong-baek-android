package com.gongbaek.android.domain.repository

import com.gongbaek.android.domain.model.Majors
import com.gongbaek.android.domain.model.Universities

interface SearchRepository {
    suspend fun searchUniversities(universityName: String): Result<Universities>
    suspend fun searchMajors(universityName: String, majorName: String): Result<Majors>
}
