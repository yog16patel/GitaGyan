package com.yogi.domain.interactors

import com.yogi.data.core.ResultInteractor
import com.yogi.data.repository.QODRepository
import javax.inject.Inject

class QODInteractor @Inject constructor(
    private val qodRepository: QODRepository
) : ResultInteractor<Unit, String?>() {
    override suspend fun doWork(params: Unit) = qodRepository.getQOD()
}