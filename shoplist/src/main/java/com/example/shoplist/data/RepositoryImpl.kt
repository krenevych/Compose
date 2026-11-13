package com.example.shoplist.data

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class RepositoryImpl : Repository {

    private val shopList = mutableListOf<ShopItem>()
    private var nextId = 0

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun add(item: ShopItem) {
        shopList.add(item.copy(id = nextId++))
    }

    override fun edit(item: ShopItem) {
        val index = shopList.indexOfFirst { it.id == item.id }
        if (index == -1) {
            throw NoSuchElementException("Shop item with id ${item.id} not found")
        }
        shopList[index] = item
    }

    override fun delete(item: ShopItem) {
        shopList.removeAll { it.id == item.id }
    }

    override fun getShopItemById(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw NoSuchElementException("Shop item with id $id not found")
    }
}
