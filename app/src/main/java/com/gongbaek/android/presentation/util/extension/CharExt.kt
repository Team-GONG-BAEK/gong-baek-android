package com.gongbaek.android.presentation.util.extension

private val VALID_KOREAN_CHAR_REGEX = Regex("^[가-힣ㆍᆞᆢㄱ-ㅎㅏ-ㅣ]*$")

fun Char.isKoreanChar(): Boolean = VALID_KOREAN_CHAR_REGEX.matches(this.toString())

fun Char.isCompleteKorean(): Boolean = this in '\uAC00'..'\uD7A3'
