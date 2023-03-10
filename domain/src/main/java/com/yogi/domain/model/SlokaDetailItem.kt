package com.yogi.domain.model

data class SlokaDetailItem(
    val chapter_number: Int = 1,
    val slokas: List<Sloka> = emptyList()
)