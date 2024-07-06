package com.yogi.data.repository

import com.yogi.data.models.ChapterDetailItem
import com.yogi.data.models.ChapterInfoItem
import com.yogi.data.core.Result

interface GitaGyanRepository {
    suspend fun getChapterList(): Result<List<ChapterInfoItem>>
    suspend fun getChapterDetails(chapterNumber: String): Result<ChapterDetailItem?>?
    suspend fun getNumberOfSloka(chapterNumber: Int): Int?
}