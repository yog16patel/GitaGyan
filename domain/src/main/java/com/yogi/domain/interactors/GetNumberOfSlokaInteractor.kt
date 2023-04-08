package com.yogi.domain.interactors

import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.repository.GitaGyanRepository
import javax.inject.Inject

class GetNumberOfSlokaInteractor @Inject constructor(
    private val gitaGyanRepository: GitaGyanRepository
)  : ResultInteractor<Int, Int?>() {
    override suspend fun doWork(chNumber: Int): Int? {
        if(chNumber !in 1..18) return 1
        return gitaGyanRepository.getNumberOfSloka(chNumber)
    }
}