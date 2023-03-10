package com.yogi.data.di

import com.yogi.data.firebase.FirebaseDatabaseImpl
import com.yogi.domain.firebase.FirebaseDatabaseInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataModule {

    @Binds
    abstract fun bindFirebaseDatabaseInterface(firebaseDatabaseImpl: FirebaseDatabaseImpl): FirebaseDatabaseInterface

}