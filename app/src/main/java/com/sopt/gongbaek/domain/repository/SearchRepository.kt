package com.sopt.gongbaek.domain.repository

import com.sopt.gongbaek.domain.model.Majors
import com.sopt.gongbaek.domain.model.Universities

interface SearchRepository {
    suspend fun searchUniversities(universityName: String): Result<Universities>
    suspend fun searchMajors(universityName: String, majorName: String): Result<Majors>
}
