package com.example.roomdb

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CardViewModel(application: Application): AndroidViewModel(application) {

    val cardDao = CardDatabase.getInstance(application).cardDao()

    fun insertCard(card: CardData) {
        viewModelScope.launch {
            cardDao.insertCard(card)
        }
    }

    fun deleteCardById(id: Int) {
        viewModelScope.launch {
            cardDao.deleteCardById(id)
        }
    }

    fun deleteCard(card: CardData) {
        viewModelScope.launch {
            cardDao.deleteCardById(card.id)
        }
    }



    val allCards = cardDao.getAllCards()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

}