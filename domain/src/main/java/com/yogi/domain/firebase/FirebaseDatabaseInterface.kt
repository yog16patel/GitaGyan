package com.yogi.domain.firebase

import com.yogi.domain.model.ChapterInfoItem
import com.yogi.domain.model.SlokaDetailItem

interface FirebaseDatabaseInterface {
    suspend fun getChapterList(): List<ChapterInfoItem>?
    suspend fun getSlokas(slokaNumber: String): SlokaDetailItem?
}