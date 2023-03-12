package com.yogi.domain.entities

data class ChapterDetailItemEntity(
    val chapterNumber: String = "",
    val slokList: List<SlokEntity> = emptyList()
)