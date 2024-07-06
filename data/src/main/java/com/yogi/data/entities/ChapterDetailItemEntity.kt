package com.yogi.data.entities

data class ChapterDetailItemEntity(
    val chapterNumber: String = "",
    val slokList: List<SlokEntity> = emptyList()
)