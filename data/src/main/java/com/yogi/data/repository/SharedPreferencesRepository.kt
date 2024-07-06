package com.yogi.data.repository

import com.yogi.data.entities.PreferredLanguage


interface SharedPreferencesRepository {

    val keyLanguage: String
    val keyLastReadSlok: String
    val keyLastReadSlokChapterIndex: String

    fun setup()
    fun saveLanguageToSharedPref(value: PreferredLanguage)
    fun getLanguageFromSharedPref(): PreferredLanguage?
    fun saveLastReadSlokMapJsonString(lastSelectedSlok: String)
    fun saveIndexOfLastReadSlokaAndChapter(indexesAsString: String)
    fun getSaveLastReadSlokMap(): String?
    fun getSaveLastReadSlokChapterIndex(): String?
}