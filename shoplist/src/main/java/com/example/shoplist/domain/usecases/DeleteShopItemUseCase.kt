package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class DeleteShopItemUseCase(private val repository: Repository) {
    fun delete(item: ShopItem) {
        repository.delete(item)
    }
}
