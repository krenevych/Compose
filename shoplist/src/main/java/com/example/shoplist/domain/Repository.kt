package com.example.shoplist.domain

interface Repository {
    fun getShopList(): List<ShopItem>
    fun add(item: ShopItem)
    fun edit(item: ShopItem)
    fun delete(item: ShopItem)
    fun getShopItemById(id: Int): ShopItem
}