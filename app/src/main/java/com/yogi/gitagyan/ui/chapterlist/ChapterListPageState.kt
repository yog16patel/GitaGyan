package com.yogi.gitagyan.ui.chapterlist

import com.yogi.gitagyan.models.ChapterInfoItemUi

data class ChapterListPageState(
    val chapterInfoItems: List<ChapterInfoItemUi> = emptyList(),
    val isLoading:Boolean = false
)