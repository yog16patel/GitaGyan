package com.yogi.data.firebase

interface FirebaseDataStore {
    fun getReferenceUrlFromChapter(chapterNumber: String): String
}