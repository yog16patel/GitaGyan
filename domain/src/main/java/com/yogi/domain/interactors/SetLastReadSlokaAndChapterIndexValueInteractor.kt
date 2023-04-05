package com.yogi.domain.interactors

import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SetLastReadSlokaAndChapterIndexValueInteractor@Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<GitaPair<Int,Int>, Unit>() {
    override suspend fun doWork(params: GitaPair<Int, Int>) {
        sharedPreferencesRepository.saveIndexOfLastReadSlokaAndChapter(params.toString())
    }
}