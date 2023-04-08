package com.yogi.gitagyan.ui.slokadetails

import com.yogi.gitagyan.models.ChapterDetailItemUi

data class SlokaDetailsPageState(
    val chapterDetailsItems: ChapterDetailItemUi? = null,
    var lastSelectedSloka: Int = 1,
    var isDetailOpen: Boolean = false,
    var totalSlokaList: List<Int> = emptyList(),
    val isLoading:Boolean = false
)