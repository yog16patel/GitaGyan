package com.yogi.domain.repository

import com.yogi.domain.entities.PreferredLanguage

interface SharedPreferencesRepository {

    val keyLanguage: String

    fun setup()
    fun saveLanguageToSharedPref(value: PreferredLanguage)
    fun getLanguageFromSharedPref() : PreferredLanguage?
}