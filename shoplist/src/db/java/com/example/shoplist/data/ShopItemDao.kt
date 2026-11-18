package com.example.shoplist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ShopItemDao {
    @Query("SELECT * FROM shop_items ORDER BY id")
    fun observeAll(): Flow<List<ShopItemEntity>>

    @Insert
    suspend fun insert(item: ShopItemEntity)

    @Update
    suspend fun update(item: ShopItemEntity): Int

    @Query("DELETE FROM shop_items WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM shop_items WHERE id = :id")
    suspend fun findById(id: Int): ShopItemEntity?
}
