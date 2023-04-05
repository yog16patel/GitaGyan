package com.yogi.domain.interactors

import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import com.yogi.domain.toPair
import javax.inject.Inject

class GetLastReadSlokaAndChapterNameInteractor @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<Unit, GitaPair<String,String>> (){
    override suspend fun doWork(params: Unit): GitaPair<String, String> {
        return sharedPreferencesRepository.getSaveLastReadSlokMap().toPair()
    }
}

