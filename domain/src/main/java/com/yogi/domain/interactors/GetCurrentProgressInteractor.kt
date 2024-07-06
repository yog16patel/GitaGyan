package com.yogi.domain.interactors

import com.yogi.data.core.ResultInteractor
import com.yogi.domain.localinfo.slokaNumberInChapterMap
import com.yogi.domain.localinfo.totalSlok
import javax.inject.Inject

class GetCurrentProgressInteractor @Inject constructor(
    private val getLastReadSlokaInteractor: GetLastReadSlokaAndChapterIndexInteractor
) : ResultInteractor<Unit, Int>() {
    override suspend fun doWork(params: Unit): Int {
        val chapterSlokPair = getLastReadSlokaInteractor.executeSync(Unit)
        val chapterNumber = chapterSlokPair.first
        val slokaNumber = chapterSlokPair.second
        var currentProgress = 0

        val lastChapterSloka = slokaNumberInChapterMap[chapterNumber]

        run breaking@{
            slokaNumberInChapterMap.forEach { (key, value) ->
                currentProgress += value
                if (key == chapterNumber) {
                    return@breaking
                }
            }
        }
        currentProgress = currentProgress.plus((slokaNumber).minus(lastChapterSloka?:0))
        return currentProgress * 100 / totalSlok
    }
}