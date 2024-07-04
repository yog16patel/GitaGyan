package com.yogi.data.di

import android.content.Context
import com.yogi.data.util.NetworkUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNetworkUtility(@ApplicationContext context: Context): NetworkUtility{
           return NetworkUtility(context = context)
    }
}