package com.yogi.gitagyan.models

data class ChapterDetailItemUi(
    val chapterNumber: String,
    val chapterTitle:String,
    val description : String = "",
    val slokUiEntityList: List<SlokUi>
)