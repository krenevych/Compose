package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class EditShopItemUseCase(private val repository: Repository) {
    fun edit(item: ShopItem) {
        repository.edit(item)
    }
}
