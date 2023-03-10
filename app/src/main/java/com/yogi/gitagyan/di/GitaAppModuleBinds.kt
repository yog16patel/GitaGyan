package com.yogi.gitagyan.di

import com.yogi.gitagyan.base.AppInitializer
import com.yogi.gitagyan.base.PreferencesInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
abstract class GitaAppModuleBinds {

    @Binds
    @IntoSet
    abstract fun bindsPreferencesInitializer(preferencesInitializer: PreferencesInitializer ): AppInitializer

}