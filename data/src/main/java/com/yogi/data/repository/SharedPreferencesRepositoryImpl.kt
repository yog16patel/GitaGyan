package com.yogi.data.repository

import android.content.SharedPreferences
import com.yogi.domain.repository.SharedPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

    override fun setup() {
        //Do nothing for now
    }

    override fun saveValueSharedPreferences(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getSharedPreferencesValues(key: String): String? = sharedPreferences.getString(key,"")
}