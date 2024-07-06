package com.yogi.data.firebase

import com.yogi.data.entities.ChapterDetailItemEntity
import com.yogi.data.entities.ChapterInfoItemEntity

interface FirebaseDatabaseInterface {
    suspend fun getChapterList(language: String): List<ChapterInfoItemEntity>?
    suspend fun getSlokasOfChapter(language: String, chapterNumber: String): ChapterDetailItemEntity?
}