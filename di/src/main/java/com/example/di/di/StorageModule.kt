package com.example.di.di

import com.example.di.computer.Hdd
import com.example.di.computer.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface StorageModule {

    @Binds
    fun provideStorage(implementation: Hdd): Storage

}