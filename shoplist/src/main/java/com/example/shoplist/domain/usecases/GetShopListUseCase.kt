package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.flow.Flow

class GetShopListUseCase(private val repository: Repository) {

    operator fun invoke(): Flow<List<ShopItem>> {
        return repository.getShopList()
    }

}