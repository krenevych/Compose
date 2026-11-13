package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class AddShopItemUseCas(private val repository: Repository) {
    fun add(item: ShopItem) {
        repository.add(item)
    }
}