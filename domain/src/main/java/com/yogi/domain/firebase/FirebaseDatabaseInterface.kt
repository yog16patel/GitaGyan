package com.yogi.domain.firebase

import com.yogi.domain.entities.ChapterDetailItemEntity
import com.yogi.domain.entities.ChapterInfoItemEntity

interface FirebaseDatabaseInterface {
    suspend fun getChapterList(language: String): List<ChapterInfoItemEntity>?
    suspend fun getSlokasOfChapter(language: String, chapterNumber: String): ChapterDetailItemEntity?
}