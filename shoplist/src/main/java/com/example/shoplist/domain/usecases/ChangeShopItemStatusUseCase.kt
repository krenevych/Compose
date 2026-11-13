package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import javax.inject.Inject
import com.example.shoplist.domain.ShopItem

class ChangeShopItemStatusUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        repository.edit(newItem)
    }
}
