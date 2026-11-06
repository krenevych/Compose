package com.example.private24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrivatViewModel : ViewModel() {

    private val TAG = "XXXX"

    private val networkModule = PrivatBankNetworkModule()

    private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState.asStateFlow()


    fun getCurrencyExchange(
        courseId: Int = 5,
    ) {
        viewModelScope.launch {
            _uiState.value =
                networkModule.service.getCurrencyExchange(courseId = courseId).toString()
        }
    }
}