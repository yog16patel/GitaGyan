package com.yogi.data.core

abstract class ResultInteractor<T,R> {

    suspend fun executeSync(params: T) = doWork(params = params)

    abstract suspend fun doWork(params: T): R
}