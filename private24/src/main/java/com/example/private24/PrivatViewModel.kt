package com.example.private24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class PrivatViewModel : ViewModel() {

    private val TAG = "XXXX"

    private val networkModule = PrivatBankNetworkModule()

    private val _uiState = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    val uiState: StateFlow<String> = _uiState.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    fun getCurrencyExchange(
        courseId: Int = 5,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(5000.milliseconds)

            _uiState.value =
                networkModule.service.getCurrencyExchange(courseId = courseId).toString()
            _isLoading.value = false
        }
    }
}