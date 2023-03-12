package com.yogi.gitagyan.ui.slokadetails

import com.yogi.gitagyan.models.ChapterDetailItemUi

data class SlokaDetailsPageState(
    val chapterDetailsItems: ChapterDetailItemUi? = null,
    var lastSelectedSloka: Int = 1,
    val isLoading:Boolean = false
)