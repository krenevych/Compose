package com.example.sharedpref

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = SettingsRepository(application.applicationContext)

    // Стан редагування змінюється одразу, а зберігається лише після Save.
    var userName by mutableStateOf("")
        private set

    var hasNotification by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadSettings()
    }

    fun updateName(newName: String) {
        userName = newName
    }

    fun updateNotification(newStatus: Boolean) {
        hasNotification = newStatus
    }

    fun loadSettings() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val (savedName, savedNotifications) = combine(
                    repository.userNameFlow,
                    repository.hasNotificationsFlow
                ) { name, notifications -> name to notifications }.first()

                userName = savedName
                hasNotification = savedNotifications
            } finally {
                isLoading = false
            }
        }
    }

    fun saveSettings() {
        val name = userName
        val notifications = hasNotification

        viewModelScope.launch {
            repository.saveUserName(name)
            repository.toggleNotifications(notifications)
        }
    }
}
