package com.yogi.domain.interactors

import com.yogi.data.core.GitaPair
import com.yogi.data.core.ResultInteractor
import com.yogi.data.repository.SharedPreferencesRepository
import com.yogi.domain.toPairInt
import javax.inject.Inject

class GetLastReadSlokaAndChapterIndexInteractor @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<Unit, GitaPair<Int, Int>>(){
    override suspend fun doWork(params: Unit): GitaPair<Int, Int> {
        return sharedPreferencesRepository.getSaveLastReadSlokChapterIndex().toPairInt()
    }
}