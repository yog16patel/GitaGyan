package com.yogi.domain.interactors

import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import com.yogi.domain.toPairInt
import javax.inject.Inject

class GetLastReadSlokaAndChapterIndexInteractor @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<Unit, GitaPair<Int, Int>>(){
    override suspend fun doWork(params: Unit): GitaPair<Int, Int> {
        return sharedPreferencesRepository.getSaveLastReadSlokChapterIndex().toPairInt()
    }
}