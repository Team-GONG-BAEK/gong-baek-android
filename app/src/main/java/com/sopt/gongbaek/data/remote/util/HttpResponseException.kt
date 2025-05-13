package com.sopt.gongbaek.data.remote.util

class HttpResponseException(val code: Int, message: String) : Exception(message)
