package com.yogi.domain.interactors

import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SetLastReadSlokaAndChapterNameInteractor@Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ResultInteractor<GitaPair<String,String>, Unit>() {
    override suspend fun doWork(params: GitaPair<String, String>) {
        sharedPreferencesRepository.saveLastReadSlokMapJsonString(params.toString())
    }
}