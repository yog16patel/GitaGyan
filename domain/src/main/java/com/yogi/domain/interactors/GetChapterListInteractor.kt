package com.yogi.domain.interactors

import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.repository.GitaGyanRepository
import javax.inject.Inject
import com.yogi.domain.core.Result
import com.yogi.domain.models.ChapterInfoItem

class GetChapterListInteractor @Inject constructor(
    private val gitaGyanRepository: GitaGyanRepository
) : ResultInteractor<Unit, Result<List<ChapterInfoItem>>>() {
    override suspend fun doWork(params: Unit): Result<List<ChapterInfoItem>> {
        return gitaGyanRepository.getChapterList()
    }
}