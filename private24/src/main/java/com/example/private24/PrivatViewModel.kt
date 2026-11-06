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

    private val _uiState = MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val uiState: StateFlow<ExchangeRatesUiState> = _uiState.asStateFlow()

    init {
        getCurrencyExchange()
    }

    fun getCurrencyExchange(
        courseId: Int = 5,
    ) {
        viewModelScope.launch {
            _uiState.value = ExchangeRatesUiState.Loading
            delay(5000.milliseconds) // For debug


            val result: List<CurrencyItem>? = networkModule.service.getCurrencyExchange(courseId = courseId)

            result?.let {result ->
                _uiState.value = ExchangeRatesUiState.Success(result)
            } ?: run {
                _uiState.value = ExchangeRatesUiState.Error
            }
        }
    }
}