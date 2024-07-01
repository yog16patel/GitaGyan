package com.yogi.gitagyan.ui

data class CurrentState(
    val selectedChapterString: String = "",
    val lastSlokString: String = "",
    val selectedChapterIndex: Int = 0,
    val selectedSlokIndex: Int = 0,
    val currentProgress: String = "0.0",
    val likedSloka: Int = 0
)
