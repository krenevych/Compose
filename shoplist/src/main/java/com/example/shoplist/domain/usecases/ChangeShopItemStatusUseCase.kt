package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class ChangeShopItemStatusUseCase(private val repository: Repository) {
    operator fun invoke(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        repository.edit(newItem)
    }
}
