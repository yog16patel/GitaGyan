package com.yogi.data.firebase


import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class FirebaseDataStoreImpl @Inject constructor(
    private val storage: StorageReference
): FirebaseDataStore {
    override fun getReferenceUrlFromChapter(chapterNumber: String): String {
        return ""
    }
}