package com.yogi.gitagyan.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GitaAppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context{
        return appContext.applicationContext
    }

    @Provides
    fun firebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun firebaseDataStore(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun sharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("GitaApp", Context.MODE_PRIVATE)
    }

}