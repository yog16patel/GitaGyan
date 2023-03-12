package com.yogi.domain.repository

import com.yogi.domain.core.Result
import com.yogi.domain.models.ChapterDetailItem
import com.yogi.domain.models.ChapterInfoItem

interface GitaGyanRepository {
    suspend fun getChapterList(): Result<List<ChapterInfoItem>>
    suspend fun getChapterDetails(chapterNumber: String): Result<ChapterDetailItem?>?
}