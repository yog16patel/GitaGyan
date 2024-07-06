package com.yogi.domain.interactors

import com.yogi.data.core.ResultInteractor
import com.yogi.data.models.ChapterInfoItem
import com.yogi.data.repository.GitaGyanRepository
import javax.inject.Inject
import com.yogi.data.core.Result

class GetChapterListInteractor @Inject constructor(
    private val gitaGyanRepository: GitaGyanRepository
) : ResultInteractor<Unit, Result<List<ChapterInfoItem>>>() {
    override suspend fun doWork(params: Unit): Result<List<ChapterInfoItem>> {
        return gitaGyanRepository.getChapterList()
    }
}