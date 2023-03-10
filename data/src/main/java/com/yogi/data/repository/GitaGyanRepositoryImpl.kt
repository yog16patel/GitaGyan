package com.yogi.data.repository

import com.yogi.domain.core.Result
import com.yogi.domain.firebase.FirebaseDatabaseInterface
import com.yogi.domain.model.ChapterInfoItem
import com.yogi.domain.model.SlokaDetailItem
import com.yogi.domain.repository.GitaGyanRepository
import java.lang.Exception
import javax.inject.Inject

class GitaGyanRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabaseInterface
) : GitaGyanRepository {
    override suspend fun getChapterList(): Result<List<ChapterInfoItem>> {
        return try {
            val response = firebaseDatabase.getChapterList() ?: emptyList()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Exception")
        }
    }

    override suspend fun getChapterDetails(chapterNumber: String): Result<SlokaDetailItem?>? {
        return try {
            val response = firebaseDatabase.getSlokas(chapterNumber)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Exception")
        }
    }
}