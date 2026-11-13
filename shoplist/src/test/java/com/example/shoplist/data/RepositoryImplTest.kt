package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {
    private val repository = RepositoryImpl()

    @Before
    fun clearFakeItems() {
        repository.getShopList().forEach(repository::delete)
    }

    @Test
    fun repositoryStartsWithFakeItemsAndAssignsANewIdOnAdd() {
        val populatedRepository = RepositoryImpl()
        val items = populatedRepository.getShopList()

        assertTrue(items.isNotEmpty())
        assertTrue(items.any { it.enabled })
        assertTrue(items.any { !it.enabled })
        assertEquals(items.size, items.map { it.id }.toSet().size)

        populatedRepository.add(ShopItem(name = "Tea"))

        val newItem = populatedRepository.getShopList().last()
        assertTrue(items.none { it.id == newItem.id })
    }

    @Test
    fun addedItemsHaveUniqueIdsThatAreNotReusedAfterDeletion() {
        repository.add(ShopItem(name = "Milk"))
        repository.add(ShopItem(name = "Bread"))
        val items = repository.getShopList()
        repository.delete(items.first())
        repository.add(ShopItem(name = "Eggs"))

        val remainingItems = repository.getShopList()
        assertEquals(listOf("Bread", "Eggs"), remainingItems.map { it.name })
        assertEquals(3, (items + remainingItems).map { it.id }.toSet().size)
    }

    @Test
    fun editUpdatesFieldsAndStatusWithoutChangingOrder() {
        repository.add(ShopItem(name = "Milk"))
        repository.add(ShopItem(name = "Bread"))
        val items = repository.getShopList()
        val editedItem = items.first().copy(name = "Oat milk", count = 2, enabled = true)

        repository.edit(editedItem)

        assertEquals(editedItem, repository.getShopItemById(editedItem.id))
        assertEquals(listOf(editedItem, items.last()), repository.getShopList())
    }

    @Test
    fun deleteMatchesIdEvenWhenOtherFieldsDiffer() {
        repository.add(ShopItem(name = "Milk"))
        val item = repository.getShopList().single()

        repository.delete(item.copy(name = "Changed name"))
        repository.delete(item)

        assertTrue(repository.getShopList().isEmpty())
    }

    @Test
    fun returnedListIsASnapshot() {
        repository.add(ShopItem(name = "Milk"))
        val snapshot = repository.getShopList()

        repository.add(ShopItem(name = "Bread"))

        assertEquals(listOf("Milk"), snapshot.map { it.name })
        assertEquals(2, repository.getShopList().size)
    }

    @Test(expected = NoSuchElementException::class)
    fun lookupOfMissingItemThrows() {
        repository.getShopItemById(42)
    }

    @Test(expected = NoSuchElementException::class)
    fun editingMissingItemThrows() {
        repository.edit(ShopItem(id = 42, name = "Milk"))
    }
}
