package com.yogi.domain.interactors

import com.yogi.domain.core.Result
import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.models.ChapterDetailItem
import com.yogi.domain.repository.GitaGyanRepository
import javax.inject.Inject

class GetSlokaDetailsInteractor @Inject constructor(
    private val gitaGyanRepository: GitaGyanRepository
)  : ResultInteractor<Int, Result<ChapterDetailItem?>?>() {
    override suspend fun doWork(chapterNumber: Int): Result<ChapterDetailItem?>? {
        if(chapterNumber !in 1..18) return Result.Error("Chapter number must be between 1 to 18!")
        return gitaGyanRepository.getChapterDetails(chapterNumber.minus(1).toString())
    }
}