package com.example.sharedpref

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application
)
    : AndroidViewModel(application) {

    private val repository = SettingsRepository(application.applicationContext)

    val userName = repository
        .userNameFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    val hasNotification = repository.hasNotificationsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun updateName(newName: String) {
        viewModelScope.launch {
            repository.saveUserName(newName)
        }
    }

    fun updateNotification(newStatus: Boolean) {
        viewModelScope.launch {
            repository.toggleNotifications(newStatus)
        }
    }

}