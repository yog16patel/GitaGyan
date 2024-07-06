package com.yogi.domain.interactors

import com.yogi.data.core.GitaPair
import com.yogi.data.core.ResultInteractor
import com.yogi.data.repository.SharedPreferencesRepository
import com.yogi.domain.toPair
import javax.inject.Inject

class GetLastReadSlokaAndChapterNameInteractor @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<Unit, GitaPair<String, String>>(){
    override suspend fun doWork(params: Unit): GitaPair<String, String> {
        return try {
            sharedPreferencesRepository.getSaveLastReadSlokMap().toPair()
        }catch (e: Exception) {
            GitaPair("1","1")
        }
    }
}

