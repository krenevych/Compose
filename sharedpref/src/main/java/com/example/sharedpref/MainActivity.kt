package com.example.sharedpref

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.example.sharedpref.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val SHARED_PREF_FILE = "app_settings"

        const val KEY_USER_NAME = "key_username"
        const val KEY_NOTIFICATION_ENABLED = "key_notifications"
    }

    // Отримуємо доступ до файлу app_settings.xml
    lateinit var sharedPref: SharedPreferences

    // Стани для зберігання значень полів
    var username by mutableStateOf("")
    var notificationsEnabled by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPref = getSharedPreferences(SHARED_PREF_FILE, MODE_PRIVATE)

        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding),
                        username = username,
                        onNameChanged = { username = it },
                        notificationsEnabled = notificationsEnabled,
                        onNotificationChanged = {notificationsEnabled = it},
                        onSavePressed = {
                            sharedPref.edit {
                                putString(KEY_USER_NAME, username)
                                putBoolean(KEY_NOTIFICATION_ENABLED, notificationsEnabled)
                            }
                        },
                        onLoadPressed = { loadData() }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        loadData()
    }



    private fun loadData() {
        username = sharedPref.getString(KEY_USER_NAME, "Unknown") ?: ""
        notificationsEnabled = sharedPref.getBoolean(KEY_NOTIFICATION_ENABLED, false)
    }

}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    username: String = "",
    onNameChanged: (String) -> Unit = {},
    notificationsEnabled: Boolean = false,
    onNotificationChanged: (Boolean) -> Unit = {},
    onSavePressed: () -> Unit = {},
    onLoadPressed: () -> Unit = {},
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Налаштування профілю",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Поле для вводу тексту (String)
        OutlinedTextField(
            value = username,
            onValueChange = onNameChanged,
            label = { Text("Ім'я користувача") },// { username = it }
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Перемикач (Boolean)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Отримувати сповіщення",
                fontSize = 18.sp
            )
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = onNotificationChanged, //{ notificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопки для Load та Save
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = onLoadPressed,//{ /* TODO: Зчитати дані з SharedPreferences */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Load")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = onSavePressed,  //{ /* TODO: Зберегти дані в SharedPreferences */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ComposeTheme {
        SettingsScreen()
    }
}
