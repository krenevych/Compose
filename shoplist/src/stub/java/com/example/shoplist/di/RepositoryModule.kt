package com.example.shoplist.di

import com.example.shoplist.data.RepositoryImpl
import com.example.shoplist.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // All consumers share the same in-memory shopping list while the app is running.
    @Binds
    @Singleton
    abstract fun bindRepository(implementation: RepositoryImpl): Repository
}
