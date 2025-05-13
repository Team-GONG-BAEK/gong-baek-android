package com.sopt.gongbaek.data.remote.util

import com.google.gson.Gson
import com.sopt.gongbaek.data.remote.dto.base.ApiResponse
import com.sopt.gongbaek.data.remote.dto.base.NullableApiResponse
import retrofit2.HttpException

object HttpResponseHandler {
    fun <T> ApiResponse<T>.handleApiResponse(): Result<T> =
        if (this.code in 2000..2999) {
            Result.success(this.data)
        } else {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }

    fun <T> NullableApiResponse<T>.handleNullableApiResponse(): Result<T?> =
        if (this.code in 2000..2999) {
            Result.success(this.data)
        } else {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }

    fun parseHttpException(e: Throwable): Exception {
        return if (e is HttpException) {
            try {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, NullableApiResponse::class.java)
                when (errorResponse.code) {
                    in 3000..3999 -> HttpResponseException(errorResponse.code, "Redirection error : ${errorResponse.message}")
                    in 4000..4999 -> HttpResponseException(errorResponse.code, "Client error : ${errorResponse.message}")
                    in 5000..5999 -> HttpResponseException(errorResponse.code, "Server error : ${errorResponse.message}")
                    else -> HttpResponseException(errorResponse.code, "Unknown error : ${errorResponse.message}")
                }
            } catch (ex: Exception) {
                Exception("Failed to parse error response.")
            }
        } else {
            Exception(e.message)
        }
    }
}
