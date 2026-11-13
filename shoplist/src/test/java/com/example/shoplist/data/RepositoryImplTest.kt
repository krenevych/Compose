package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {
    private val repository = RepositoryImpl()

    @Before
    fun clearFakeItems() = runBlocking<Unit> {
        repository.getShopList().first().forEach { repository.delete(it) }
    }

    @Test
    fun repositoryStartsWithFakeItemsAndAssignsANewIdOnAdd() = runBlocking<Unit> {
        val populatedRepository = RepositoryImpl()
        val items = populatedRepository.getShopList().first()

        assertTrue(items.isNotEmpty())
        assertTrue(items.any { it.enabled })
        assertTrue(items.any { !it.enabled })
        assertEquals(items.size, items.map { it.id }.toSet().size)

        populatedRepository.add(ShopItem(name = "Tea"))

        val newItem = populatedRepository.getShopList().first().last()
        assertTrue(items.none { it.id == newItem.id })
    }

    @Test
    fun addedItemsHaveUniqueIdsThatAreNotReusedAfterDeletion() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        repository.add(ShopItem(name = "Bread"))
        val items = repository.getShopList().first()
        repository.delete(items.first())
        repository.add(ShopItem(name = "Eggs"))

        val remainingItems = repository.getShopList().first()
        assertEquals(listOf("Bread", "Eggs"), remainingItems.map { it.name })
        assertEquals(3, (items + remainingItems).map { it.id }.toSet().size)
    }

    @Test
    fun editUpdatesFieldsAndStatusWithoutChangingOrder() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        repository.add(ShopItem(name = "Bread"))
        val items = repository.getShopList().first()
        val editedItem = items.first().copy(name = "Oat milk", count = 2, enabled = true)

        repository.edit(editedItem)

        assertEquals(editedItem, repository.getShopItemById(editedItem.id))
        assertEquals(listOf(editedItem, items.last()), repository.getShopList().first())
    }

    @Test
    fun deleteMatchesIdEvenWhenOtherFieldsDiffer() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        val item = repository.getShopList().first().single()

        repository.delete(item.copy(name = "Changed name"))
        repository.delete(item)

        assertTrue(repository.getShopList().first().isEmpty())
    }

    @Test
    fun returnedListIsASnapshot() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        val snapshot = repository.getShopList().first()

        repository.add(ShopItem(name = "Bread"))

        assertEquals(listOf("Milk"), snapshot.map { it.name })
        assertEquals(2, repository.getShopList().first().size)
    }

    @Test(expected = NoSuchElementException::class)
    fun lookupOfMissingItemThrows() = runBlocking<Unit> {
        repository.getShopItemById(42)
    }

    @Test(expected = NoSuchElementException::class)
    fun editingMissingItemThrows() = runBlocking<Unit> {
        repository.edit(ShopItem(id = 42, name = "Milk"))
    }
}
