package com.yogi.domain.repository

import com.yogi.domain.core.Result
import com.yogi.domain.model.ChapterInfoItem
import com.yogi.domain.model.SlokaDetailItem

interface GitaGyanRepository {
    suspend fun getChapterList(): Result<List<ChapterInfoItem>>
    suspend fun getChapterDetails(chapterNumber: String): Result<SlokaDetailItem?>?
}