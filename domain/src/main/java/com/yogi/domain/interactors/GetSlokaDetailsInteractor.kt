package com.yogi.domain.interactors

import com.yogi.data.core.ResultInteractor
import com.yogi.data.models.ChapterDetailItem
import com.yogi.data.repository.GitaGyanRepository
import com.yogi.data.core.Result
import javax.inject.Inject

class GetSlokaDetailsInteractor @Inject constructor(
    private val gitaGyanRepository: GitaGyanRepository
)  : ResultInteractor<Int, Result<ChapterDetailItem?>?>() {
    override suspend fun doWork(chapterNumber: Int): Result<ChapterDetailItem?>? {
        if(chapterNumber !in 1..18) return Result.Error("Chapter number must be between 1 to 18!")
        return gitaGyanRepository.getChapterDetails(chapterNumber.minus(1).toString())
    }
}