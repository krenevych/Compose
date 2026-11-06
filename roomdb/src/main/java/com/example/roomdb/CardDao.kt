package com.example.roomdb

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardData)

    @Query("DELETE FROM card_table WHERE id = :id")
    suspend fun deleteCardById(id: Int)

    @Query("SELECT * FROM card_table ORDER BY id ASC")
    fun getAllCards(): Flow<List<CardData>>
}