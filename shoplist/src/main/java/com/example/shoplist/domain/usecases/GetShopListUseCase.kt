package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.Repository
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.shopList
}