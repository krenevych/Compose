package com.example.shoplist.data

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepositoryImpl : Repository {

    private val items = mutableListOf<ShopItem>()

    private val _shopListFlow = MutableStateFlow(items.toList())
    override val shopList: StateFlow<List<ShopItem>> = _shopListFlow.asStateFlow()

    private var nextId = 0

    init {
        addItem(ShopItem(name = "Молоко", count = 2, enabled = true))
        addItem(ShopItem(name = "Хліб", count = 1, enabled = true))
        addItem(ShopItem(name = "Яблука", count = 5, enabled = false))
        addItem(ShopItem(name = "Яйця", count = 10, enabled = true))
        addItem(ShopItem(name = "Кава", count = 1, enabled = false))
    }

    override suspend fun add(item: ShopItem) {
        addItem(item)
    }

    private fun addItem(item: ShopItem) {
        items.add(item.copy(id = nextId++))
        _shopListFlow.value = items.toList()
    }

    override suspend fun edit(item: ShopItem) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index == -1) {
            throw NoSuchElementException("Shop item with id ${item.id} not found")
        }
        items[index] = item
        _shopListFlow.value = items.toList()
    }

    override suspend fun delete(item: ShopItem) {
        items.removeAll { it.id == item.id }
        _shopListFlow.value = items.toList()
    }

    override suspend fun getShopItemById(id: Int): ShopItem {
        return items.find { it.id == id }
            ?: throw NoSuchElementException("Shop item with id $id not found")
    }
}
