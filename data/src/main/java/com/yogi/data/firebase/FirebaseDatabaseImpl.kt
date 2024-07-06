package com.yogi.data.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.yogi.data.extensions.await
import com.yogi.data.entities.ChapterDetailItemEntity
import com.yogi.data.entities.ChapterInfoItemEntity
import javax.inject.Inject

class FirebaseDatabaseImpl @Inject constructor(
    private val database: FirebaseDatabase
): FirebaseDatabaseInterface {
    override suspend fun getChapterList(
        language: String
    ): List<ChapterInfoItemEntity>? {
        val response = database.reference.child(language).child("chapters")
            .get()
            .await()

        return response.getValue<ArrayList<ChapterInfoItemEntity>>()
    }

    override suspend fun getSlokasOfChapter(language: String, chapterNumber: String): ChapterDetailItemEntity? {
        val response = database.reference.child(language).child("Gita")
            .child(chapterNumber)
            .get()
            .await()

        return response.getValue<ChapterDetailItemEntity>()
    }
}