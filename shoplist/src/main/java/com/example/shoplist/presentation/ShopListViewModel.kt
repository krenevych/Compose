package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.RepositoryImpl
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.GetShopListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ShopListViewModel : ViewModel() {
    // Навчальний етап: створюємо репозиторій тут, пізніше додамо DI.
    private val repository = RepositoryImpl()
    private val getShopListUseCase = GetShopListUseCase(repository)

    val shopList: StateFlow<List<ShopItem>> = getShopListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
