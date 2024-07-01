package com.yogi.domain.firebase

interface FirebaseDataStore {
    fun getReferenceUrlFromChapter(chapterNumber: String): String
}