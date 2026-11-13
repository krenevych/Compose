package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import javax.inject.Inject
import com.example.shoplist.domain.ShopItem

class AddShopItemUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(item: ShopItem) {
        repository.add(item)
    }
}