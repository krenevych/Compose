package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class GetShopListUseCase(private val repository: Repository) {

    operator fun invoke(): List<ShopItem> {
        return repository.getShopList()
    }

}