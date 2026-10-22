package com.example.broadcastflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.broadcastflow.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

var receivedMessage by mutableStateOf("Hello")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BroadcastScreen(
                        receivedMessage = receivedMessage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun BroadcastScreen(
    receivedMessage: String,
    modifier: Modifier = Modifier,
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
            onClick = {
                TODO()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Send broadcast1")
        }

        Button(
            onClick = {
                TODO()
            },
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
        BroadcastScreen(receivedMessage = "Preview status text")
    }
}