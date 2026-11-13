package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.AddShopItemUseCase
import com.example.shoplist.domain.usecases.ChangeShopItemStatusUseCase
import com.example.shoplist.domain.usecases.GetShopListUseCase
import com.example.shoplist.domain.usecases.EditShopItemUseCase
import com.example.shoplist.domain.usecases.DeleteShopItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val changeShopItemStatusUseCase: ChangeShopItemStatusUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
) : ViewModel() {
    private val _editor = MutableStateFlow<ShopItemEditorState?>(null)
    val editor: StateFlow<ShopItemEditorState?> = _editor.asStateFlow()

    fun startAdding() {
        _editor.value = ShopItemEditorState()
    }

    fun startEditing(item: ShopItem) {
        _editor.value = ShopItemEditorState(item = item)
    }

    fun dismissEditor() {
        if (_editor.value?.isSaving != true) _editor.value = null
    }

    fun saveItem(name: String, count: Int) {
        val editor = _editor.value ?: return
        if (editor.isSaving || name.isBlank() || count <= 0) return
        _editor.value = editor.copy(isSaving = true)
        viewModelScope.launch {
            val item = editor.item?.copy(name = name.trim(), count = count)
                ?: ShopItem(name = name.trim(), count = count, enabled = true)
            if (editor.item == null) addShopItemUseCase(item) else editShopItemUseCase(item)
            _editor.value = null
        }
    }

    fun deleteItem(item: ShopItem) {
        viewModelScope.launch { deleteShopItemUseCase(item) }
    }

    fun toggleItemStatus(item: ShopItem) {
        viewModelScope.launch { changeShopItemStatusUseCase(item) }
    }

    val shopList: StateFlow<List<ShopItem>> = getShopListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}

data class ShopItemEditorState(
    val item: ShopItem? = null,
    val isSaving: Boolean = false
)
