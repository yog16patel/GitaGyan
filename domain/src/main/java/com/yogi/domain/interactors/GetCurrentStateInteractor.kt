package com.yogi.domain.interactors

import com.yogi.domain.core.ResultInteractor
import com.yogi.domain.entities.CurrentStatusEntity
import javax.inject.Inject

class GetCurrentStateInteractor @Inject constructor(
    private val currentProgressInteractor: GetCurrentProgressInteractor,
    private val lastReadSlokaAndChapterNameInteractor: GetLastReadSlokaAndChapterNameInteractor,
    private val lastReadSlokaAndChapterIndexInteractor: GetLastReadSlokaAndChapterIndexInteractor
) : ResultInteractor<Unit, CurrentStatusEntity>() {
    override suspend fun doWork(params: Unit): CurrentStatusEntity {
        val currentProgress = currentProgressInteractor.doWork(Unit)
        val lasReadSlokChapterName = lastReadSlokaAndChapterNameInteractor.doWork(Unit)
        val lastReadSlokChapterIndex = lastReadSlokaAndChapterIndexInteractor.doWork(Unit)

        return CurrentStatusEntity(
            selectedChapterString = lasReadSlokChapterName.first,
            lastSlokString = lasReadSlokChapterName.second,
            selectedChapterIndex = lastReadSlokChapterIndex.first,
            selectedSlokIndex = lastReadSlokChapterIndex.second,
            currentProgress = currentProgress.toString(),
            likedSloka = 0
        )

    }
}