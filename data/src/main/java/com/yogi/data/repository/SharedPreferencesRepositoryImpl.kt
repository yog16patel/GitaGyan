package com.yogi.data.repository

import android.content.SharedPreferences
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.domain.repository.SharedPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

    override val keyLanguage: String
        get() = "LANGUAGE"
    override fun setup() {
        //Do nothing for now
    }

    override fun saveLanguageToSharedPref(value: PreferredLanguage) {
        with(sharedPreferences.edit()) {
            putString(keyLanguage, value.languageName)
            apply()
        }
    }

    override fun getLanguageFromSharedPref(): PreferredLanguage {
        val defaultLanguage = sharedPreferences.getString(keyLanguage,PreferredLanguage.ENGLISH.languageName)
        return PreferredLanguage.languageNameToEnum(defaultLanguage)
    }
}