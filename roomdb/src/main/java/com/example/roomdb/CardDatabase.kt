package com.example.roomdb

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver

// Вказуємо список усіх таблиць (entities) та версію бази даних
@Database(entities = [CardData::class], version = 1)
abstract class CardDatabase : RoomDatabase() {

    // Метод, через який ми отримуватимемо доступ до нашого DAO
    abstract fun cardDao(): CardDao

    companion object {
        // Запобігаємо створенню кількох екземплярів бази в пам'яті (Singleton)
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            return INSTANCE ?: synchronized(this) {
                // Подвійна перевірка (Double-checked locking) для потокобезпеки
                INSTANCE?.let { return it }

                val instance = Room.databaseBuilder<CardDatabase>(
                    context.applicationContext,
                    "Cards" // Назва фізичного файлу бази на диску
                )
                    .setDriver(AndroidSQLiteDriver())
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}