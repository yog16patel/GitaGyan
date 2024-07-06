package com.yogi.gitagyan.base

import android.app.Application
import com.yogi.data.repository.SharedPreferencesRepository
import javax.inject.Inject

class PreferencesInitializer @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
): AppInitializer {
    override fun init(application: Application) {
        sharedPreferencesRepository.setup()
    }
}