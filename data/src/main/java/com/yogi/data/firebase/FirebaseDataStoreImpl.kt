package com.yogi.data.firebase

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.yogi.domain.firebase.FirebaseDataStore
import javax.inject.Inject

class FirebaseDataStoreImpl @Inject constructor(
    private val storage: StorageReference
): FirebaseDataStore {
    override fun getReferenceUrlFromChapter(chapterNumber: String): String {
        return ""
    }
}