package com.sopt.gongbaek.presentation.util

fun formatTimeLeft(timeLeft: Int): String = "%02d:%02d".format(timeLeft / 60, timeLeft % 60)
