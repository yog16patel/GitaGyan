package com.yogi.gitagyan

import android.app.Application
import android.content.Context
import com.yogi.gitagyan.base.AppInitializer
import com.yogi.gitagyan.base.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GitaGyanApplication: Application(){
    @Inject lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}