package com.example.workmanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.compose.runtime.livedata.observeAsState
import androidx.work.Data
import com.example.workmanager.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DataSyncScreen(
                        context = this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun sync(
    context: Context,
    requiresWiFi: Boolean = false,
    requiresCharging: Boolean = false
) {
    val constraintsBuilder = Constraints.Builder().apply {
        if (requiresWiFi)
            setRequiredNetworkType(NetworkType.UNMETERED) // Тільки на Wi-Fi (безлімітний трафік)

        if (requiresCharging){
            setRequiresCharging(true)
        }
    }
    val constraints = constraintsBuilder.build()

    val inputData = Data.Builder()
        .putString("server_url", "https://api.example.com/sync")
        .putBoolean("something", true)
        .build()

    val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(constraints) // Додаємо умови
        .setInputData(inputData)
        .build()

    // 3. Ставимо задачу у системну чергу WorkManager
    // Використовуємо enqueueUniqueWork, щоб не створити кілька однакових задач
    WorkManager.getInstance(context).enqueueUniqueWork(
        "SyncWork",
        ExistingWorkPolicy.REPLACE,
        syncRequest
    )
}

@Composable
fun DataSyncScreen(
    context: Context,
    modifier: Modifier = Modifier,
) {
    val workManager = remember { WorkManager.getInstance(context) }
    val workInfos by workManager.getWorkInfosForUniqueWorkLiveData("SyncWork").observeAsState(initial = emptyList())
    val currentWorkInfo = workInfos.firstOrNull()

    // Стан для Constraints (Умов виконання)
    var requiresWiFi by remember { mutableStateOf(false) }
    var requiresCharging by remember { mutableStateOf(false) }
    
    // Динамічний статус замість статичного
    val syncStatus = when (currentWorkInfo?.state) {
        WorkInfo.State.ENQUEUED -> "Очікує виконання..."
        WorkInfo.State.RUNNING -> "Виконується"
        WorkInfo.State.SUCCEEDED -> "Готово!"
        WorkInfo.State.FAILED -> "Помилка синхронізації"
        WorkInfo.State.BLOCKED -> "Заблоковано іншою роботою"
        WorkInfo.State.CANCELLED -> "Синхронізацію скасовано"
        null -> "Очікує запуску..."
    }

    // Отримуємо прогрес з WorkInfo
    val progressInt = currentWorkInfo?.progress?.getInt("synchronized", 0) ?: 0
    val progressFloat = progressInt / 100f

    // Чи працює або очікує задача
    val isRunningOrEnqueued = currentWorkInfo?.state == WorkInfo.State.RUNNING || 
                              currentWorkInfo?.state == WorkInfo.State.ENQUEUED

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "☁️ Резервне копіювання",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Картка з налаштуваннями Constraints
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Умови виконання (Constraints):",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = requiresWiFi,
                        onCheckedChange = { requiresWiFi = it }
                    )
                    Text("Тільки через Wi-Fi (UNMETERED)")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = requiresCharging,
                        onCheckedChange = { requiresCharging = it }
                    )
                    Text("Тільки на зарядці (CHARGING)")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Статус воркера
        Text(
            text = "Статус синхронізації:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = syncStatus,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Симуляція прогресу (буде відображатися, коли статус "RUNNING")
        if (currentWorkInfo?.state == WorkInfo.State.RUNNING) {
            LinearProgressIndicator(
                progress = { progressFloat },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$progressInt%")
        } else {
            Spacer(modifier = Modifier.height(16.dp)) // Заглушка, щоб висота не стрибала
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Кнопка для запуску
        Button(
            onClick = {
                sync(context, requiresWiFi, requiresCharging)
            },
            enabled = !isRunningOrEnqueued,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(if (isRunningOrEnqueued) "Працює..." else "Почати бекап")
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для скасування
        Button(
            onClick = { 
                workManager.cancelUniqueWork("SyncWork")
            },
            enabled = isRunningOrEnqueued,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Скасувати")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataSyncScreenPreview() {
    ComposeTheme {
        DataSyncScreen(context = LocalContext.current)
    }
}
