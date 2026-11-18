package com.example.shoplist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShopItemEntity::class], version = 1, exportSchema = false)
internal abstract class ShopListDatabase : RoomDatabase() {
    abstract fun shopItemDao(): ShopItemDao
}
