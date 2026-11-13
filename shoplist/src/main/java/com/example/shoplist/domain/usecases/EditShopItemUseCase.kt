package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class EditShopItemUseCase(private val repository: Repository) {
    suspend operator fun invoke(item: ShopItem) {
        repository.edit(item)
    }
}
