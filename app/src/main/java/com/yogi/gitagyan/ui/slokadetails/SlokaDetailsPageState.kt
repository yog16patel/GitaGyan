package com.yogi.gitagyan.ui.slokadetails

import com.yogi.gitagyan.models.ChapterDetailItemUi

data class SlokaDetailsPageState(
    val chapterInfoItems: ChapterDetailItemUi? = null,
    val isLoading:Boolean = false
)