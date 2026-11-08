package com.example.sharedpref

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.example.sharedpref.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding),
                        username = viewModel.userName,
                        notificationsEnabled = viewModel.hasNotification,
                        isLoading = viewModel.isLoading,
                        onNameChanged = viewModel::updateName,
                        onNotificationChanged = viewModel::updateNotification,
                        onSavePressed = viewModel::saveSettings,
                        onLoadPressed = viewModel::loadSettings,
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    username: String,
    notificationsEnabled: Boolean,
    onNameChanged: (String) -> Unit,
    onNotificationChanged: (Boolean) -> Unit,
    onSavePressed: () -> Unit,
    onLoadPressed: () -> Unit,
    isLoading: Boolean = false,
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
            enabled = !isLoading,
            label = { Text("Ім'я користувача") },
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
                onCheckedChange = onNotificationChanged,
                enabled = !isLoading,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопки для Load та Save
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = onLoadPressed,
                enabled = !isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text("Load")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = onSavePressed,
                enabled = !isLoading,
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
    var username by remember { mutableStateOf("Андрій") }
    var notificationsEnabled by remember { mutableStateOf(true) }

    ComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SettingsScreen(
                modifier = Modifier.padding(innerPadding),
                username = username,
                notificationsEnabled = notificationsEnabled,
                onNameChanged = { username = it },
                onNotificationChanged = { notificationsEnabled = it },
                onSavePressed = {},
                onLoadPressed = {},
            )
        }
    }
}
