package com.yogi.domain.interactors

import com.yogi.data.core.ResultInteractor
import com.yogi.data.entities.CurrentStatusEntity
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