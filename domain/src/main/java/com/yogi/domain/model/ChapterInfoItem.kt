package com.yogi.domain.model

data class ChapterInfoItem(
    val chapter_number: Int = 0,
    val translations: Translations = Translations.NO_OP
)