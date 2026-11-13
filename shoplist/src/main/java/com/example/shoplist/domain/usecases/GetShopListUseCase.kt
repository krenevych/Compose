package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository

class GetShopListUseCase(private val repository: Repository) {
    operator fun invoke() = repository.shopList
}