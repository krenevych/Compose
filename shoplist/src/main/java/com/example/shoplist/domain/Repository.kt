package com.example.shoplist.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getShopList(): Flow<List<ShopItem>>
//    val shopList: Flow<List<ShopItem>>
    suspend fun add(item: ShopItem)
    suspend fun edit(item: ShopItem)
    suspend fun delete(item: ShopItem)
    suspend fun getShopItemById(id: Int): ShopItem
}