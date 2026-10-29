package com.example.sharedpref

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // Отримуємо екземпляр SharedPreferences
    private val sharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // StateFlow для імені користувача
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    // StateFlow для стану сповіщень
    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    init {
        // При запуску ViewModel автоматично підвантажуємо останні збережені дані
        loadSettings()
    }

    // Оновлення стану (лише в оперативній пам'яті, не в SharedPreferences)
    fun updateUsername(newName: String) {
        _username.value = newName
    }

    fun updateNotifications(isEnabled: Boolean) {
        _notificationsEnabled.value = isEnabled
    }

    // Зберігання даних у SharedPreferences
    fun saveSettings() {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, _username.value)
        editor.putBoolean(KEY_NOTIFICATIONS, _notificationsEnabled.value)

        // apply() зберігає дані асинхронно у фоновому потоці (на відміну від commit())
        editor.apply()
    }

    // Завантаження даних із SharedPreferences
    fun loadSettings() {
        // Другий параметр - це значення за замовчуванням (якщо такого ключа ще немає)
        _username.value = sharedPreferences.getString(KEY_USERNAME, "") ?: ""
        _notificationsEnabled.value = sharedPreferences.getBoolean(KEY_NOTIFICATIONS, false)
    }

    companion object {
        private const val PREFS_NAME = "app_settings"
        private const val KEY_USERNAME = "key_username"
        private const val KEY_NOTIFICATIONS = "key_notifications"
    }
}
