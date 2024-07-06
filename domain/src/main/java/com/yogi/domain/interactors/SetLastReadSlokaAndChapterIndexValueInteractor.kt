package com.yogi.domain.interactors

import com.yogi.data.core.GitaPair
import com.yogi.data.core.ResultInteractor
import com.yogi.data.repository.SharedPreferencesRepository
import javax.inject.Inject

class SetLastReadSlokaAndChapterIndexValueInteractor@Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<GitaPair<Int,Int>, Unit>() {
    override suspend fun doWork(params: GitaPair<Int, Int>) {
        sharedPreferencesRepository.saveIndexOfLastReadSlokaAndChapter(params.toString())
    }
}