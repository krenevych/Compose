package com.example.di.di

import com.example.di.computer.Ssd
import com.example.di.computer.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    fun provideStorage(): Storage {
        return Ssd()
    }

}