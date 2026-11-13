package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.GetShopListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase
) : ViewModel() {

    val shopList: StateFlow<List<ShopItem>> = getShopListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
