package com.example.sharedpref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val SHARED_PREF_FILE = "app_settings"
// Extension для стандартного класу Context
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREF_FILE)

class SettingsRepository(
    private val context: Context
) {

    // 1. ЧИТАННЯ ДАНИХ (Повертає реактивний Flow)
    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            // Якщо ключа немає, повертаємо дефолтне значення "Гість"
            preferences[KEY_USER_NAME] ?: ""
        }

    val hasNotificationsFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_NOTIFICATION_ENABLED] ?: false
        }

    // 2. ЗАПИС ДАНИХ (Асинхронна suspend функція)
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_NAME] = name
        }
    }

    suspend fun toggleNotifications(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_NOTIFICATION_ENABLED] = isEnabled
        }
    }

    companion object PreferencesKeys{
        val KEY_USER_NAME = stringPreferencesKey("key_username")
        val KEY_NOTIFICATION_ENABLED = booleanPreferencesKey("key_notifications")
    }

}