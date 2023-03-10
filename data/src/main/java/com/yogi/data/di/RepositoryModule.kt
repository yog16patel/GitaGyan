package com.yogi.data.di

import com.yogi.data.repository.GitaGyanRepositoryImpl
import com.yogi.data.repository.SharedPreferencesRepositoryImpl
import com.yogi.domain.repository.GitaGyanRepository
import com.yogi.domain.repository.SharedPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindGitaGyanRepository(gitaGyanRepositoryImpl: GitaGyanRepositoryImpl): GitaGyanRepository

    @Binds
    abstract fun bindSharedPreferenceRepository(sharedPreferencesRepository: SharedPreferencesRepositoryImpl): SharedPreferencesRepository
}