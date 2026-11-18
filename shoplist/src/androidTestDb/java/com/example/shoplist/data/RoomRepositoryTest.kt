package com.example.shoplist.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.shoplist.domain.ShopItem
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomRepositoryTest {
    @Test
    fun itemSurvivesReopeningDatabase() = runBlocking<Unit> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val databaseName = "shoplist-test-${UUID.randomUUID()}.db"
        val builder = Room.databaseBuilder(context, ShopListDatabase::class.java, databaseName)
        var database = builder.build()

        try {
            val repository = RoomRepository(database.shopItemDao())
            repository.add(ShopItem(name = "Milk", count = 2, enabled = true))
            val savedItem = repository.shopList.first().single()
            database.close()

            database = builder.build()
            val reopenedRepository = RoomRepository(database.shopItemDao())
            assertEquals(listOf(savedItem), reopenedRepository.shopList.first())
        } finally {
            database.close()
            context.deleteDatabase(databaseName)
        }
    }
}
