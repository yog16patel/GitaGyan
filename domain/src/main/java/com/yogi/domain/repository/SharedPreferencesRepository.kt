package com.yogi.domain.repository

interface SharedPreferencesRepository {
    fun setup()
    fun saveValueSharedPreferences(key: String, value: String)
    fun getSharedPreferencesValues(key: String) : String?
}