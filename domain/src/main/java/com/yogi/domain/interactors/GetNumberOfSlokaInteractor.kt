package com.yogi.domain.interactors


import com.yogi.data.core.ResultInteractor
import com.yogi.data.repository.GitaGyanRepository
import javax.inject.Inject

class GetNumberOfSlokaInteractor @Inject constructor(
    private val gitaGyanRepository: GitaGyanRepository
)  : ResultInteractor<Int, Int?>() {
    override suspend fun doWork(chNumber: Int): Int? {
        if(chNumber !in 1..18) return 1
        return gitaGyanRepository.getNumberOfSloka(chNumber)
    }
}