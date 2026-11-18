package com.example.shoplist.di

import android.content.Context
import androidx.room.Room
import com.example.shoplist.data.RoomRepository
import com.example.shoplist.data.ShopListDatabase
import com.example.shoplist.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    // The singleton repository shares one Room instance across the app.
    @Provides
    @Singleton
    fun provideRepository(@ApplicationContext context: Context): Repository {
        val database = Room.databaseBuilder(context, ShopListDatabase::class.java, "shop-list.db")
            .build()
        return RoomRepository(database.shopItemDao())
    }
}
