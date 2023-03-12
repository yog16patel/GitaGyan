package com.yogi.gitagyan.ui.mappers

import com.yogi.domain.models.ChapterDetailItem
import com.yogi.domain.models.ChapterInfoItem
import com.yogi.domain.models.Slok
import com.yogi.gitagyan.models.ChapterDetailItemUi
import com.yogi.gitagyan.models.ChapterInfoItemUi
import com.yogi.gitagyan.models.SlokUi

fun Slok.toSlokUi() = SlokUi(
        slokaNumber = slokaNumber,
        slokaSanskrit = slokaSanskrit,
        slokaTranslation = slokaTranslation
)

fun ChapterInfoItem.toChapterInfoItemUi() = ChapterInfoItemUi(
    chapterNumber = chapterNumber,
    chapterNumberTitle = chapterNumberTitle,
    name = name,
    translation = translation,
    description = description,
    language = language
)

fun List<ChapterInfoItem>.toChapterInfoItemUiList() =
    this.map {
        it.toChapterInfoItemUi()
    }



fun ChapterDetailItem.toChapterDetailItemUi(title:String, description: String) = ChapterDetailItemUi(
    chapterNumber = chapterNumber,
    chapterTitle = title,
    description = description,
    slokUiEntityList = slokEntityList.map {
        it.toSlokUi()
    }
)