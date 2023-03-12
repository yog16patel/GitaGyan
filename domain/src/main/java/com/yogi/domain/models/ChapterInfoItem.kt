package com.yogi.domain.models

data class ChapterInfoItem(
    val chapterNumber: String = "",
    val chapterNumberTitle: String = "",
    val description: String,
    val language: String,
    val name: String,
    val translation: String
)