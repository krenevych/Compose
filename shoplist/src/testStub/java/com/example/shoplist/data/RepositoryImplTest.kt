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
        repository.shopList.first().forEach { repository.delete(it) }
    }

    @Test
    fun repositoryStartsWithFakeItemsAndAssignsANewIdOnAdd() = runBlocking<Unit> {
        val populatedRepository = RepositoryImpl()
        val items = populatedRepository.shopList.first()

        assertTrue(items.isNotEmpty())
        assertTrue(items.any { it.enabled })
        assertTrue(items.any { !it.enabled })
        assertEquals(items.size, items.map { it.id }.toSet().size)

        populatedRepository.add(ShopItem(name = "Tea"))

        val newItem = populatedRepository.shopList.first().last()
        assertTrue(items.none { it.id == newItem.id })
    }

    @Test
    fun addedItemsHaveUniqueIdsThatAreNotReusedAfterDeletion() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        repository.add(ShopItem(name = "Bread"))
        val items = repository.shopList.first()
        repository.delete(items.first())
        repository.add(ShopItem(name = "Eggs"))

        val remainingItems = repository.shopList.first()
        assertEquals(listOf("Bread", "Eggs"), remainingItems.map { it.name })
        assertEquals(3, (items + remainingItems).map { it.id }.toSet().size)
    }

    @Test
    fun editUpdatesFieldsAndStatusWithoutChangingOrder() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        repository.add(ShopItem(name = "Bread"))
        val items = repository.shopList.first()
        val editedItem = items.first().copy(name = "Oat milk", count = 2, enabled = true)

        repository.edit(editedItem)

        assertEquals(editedItem, repository.getShopItemById(editedItem.id))
        assertEquals(listOf(editedItem, items.last()), repository.shopList.first())
    }

    @Test
    fun deleteMatchesIdEvenWhenOtherFieldsDiffer() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        val item = repository.shopList.first().single()

        repository.delete(item.copy(name = "Changed name"))
        repository.delete(item)

        assertTrue(repository.shopList.first().isEmpty())
    }

    @Test
    fun returnedListIsASnapshot() = runBlocking<Unit> {
        repository.add(ShopItem(name = "Milk"))
        val snapshot = repository.shopList.first()

        repository.add(ShopItem(name = "Bread"))

        assertEquals(listOf("Milk"), snapshot.map { it.name })
        assertEquals(2, repository.shopList.first().size)
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
