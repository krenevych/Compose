package com.example.private24

sealed class ExchangeRatesUiState {
    data class Success(val rates: List<CurrencyItem>) : ExchangeRatesUiState()
    data object Loading : ExchangeRatesUiState()
    data object Error : ExchangeRatesUiState()
}