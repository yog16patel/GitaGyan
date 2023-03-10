package com.yogi.data.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.yogi.data.extensions.await
import com.yogi.domain.firebase.FirebaseDatabaseInterface
import com.yogi.domain.model.ChapterInfoItem
import com.yogi.domain.model.SlokaDetailItem
import javax.inject.Inject

class FirebaseDatabaseImpl @Inject constructor(
    private val database: FirebaseDatabase
): FirebaseDatabaseInterface {
    override suspend fun getChapterList(): List<ChapterInfoItem>? {
        val response = database.reference.child("chapters")
            .get()
            .await()

        return response.getValue<ArrayList<ChapterInfoItem>>()
    }

    override suspend fun getSlokas(slokaNumber: String): SlokaDetailItem? {
        val response = database.reference.child("Geeta")
            .child(slokaNumber)
            .get()
            .await()

        return response.getValue<SlokaDetailItem>()
    }
}