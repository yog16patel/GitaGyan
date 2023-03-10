package com.yogi.gitagyan.ui.chapterlist

import com.yogi.domain.model.ChapterInfoItem

data class ChapterListPageState(
    val chapterInfoItems: List<ChapterInfoItem> = emptyList()
)