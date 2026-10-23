package com.example.broadcastflow

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.broadcastflow.ui.theme.ComposeTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TrackerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val receivedMessage by viewModel.tracker.collectAsStateWithLifecycle()

                    BroadcastScreen(
                        receivedMessage = receivedMessage,
                        modifier = Modifier.padding(innerPadding),
                        onBroadcast1 = { sendBroadcast(this, ACTION_BROADCAST_1) },
                        onBroadcast2 = { sendBroadcast(this, ACTION_BROADCAST_2) }
                    )
                }
            }
        }
    }

}

fun sendBroadcast(context: Context, action: String) {
    val intent = Intent(action).apply {
        setPackage("com.example.broadcastflow")
    }
    context.sendBroadcast(intent)
}

@Composable
fun BroadcastScreen(
    receivedMessage: String,
    modifier: Modifier = Modifier,
    onBroadcast1: () -> Unit = {},
    onBroadcast2: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Text(
            text = "Broadcast",
            style = MaterialTheme.typography.headlineLarge
        )

        Button(
            onClick = onBroadcast1,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Send broadcast1")
        }

        Button(
            onClick = onBroadcast2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Send broadcast 2")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Status:",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = receivedMessage,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BroadcastScreenPreview() {
    ComposeTheme {
        BroadcastScreen(
            receivedMessage = "Preview status text",
        )
    }
}