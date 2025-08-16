package com.gongbaek.android.presentation.util.extension

fun String.hasCompleteKoreanCharacters(minLength: Int): Boolean {
    val completeKoreanChars = this.filter { it.isCompleteKorean() }
    return completeKoreanChars.length >= minLength
}
