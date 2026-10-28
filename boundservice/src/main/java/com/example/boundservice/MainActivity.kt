package com.example.boundservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.boundservice.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MediaPlayerScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MediaPlayerScreen(modifier: Modifier = Modifier) {
    // Тимчасовий стан для UI (потім ми підключимо це до Bound Service)
    var isPlaying by remember { mutableStateOf(false) }
    var currentTrack by remember { mutableStateOf("Track 1 - Unknown Artist") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Інформація про поточний трек
        Text(
            text = "Now Playing",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = currentTrack,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Кнопки управління медіаплеєром
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Кнопка Previous
            IconButton(
                onClick = { /* TODO: Зв'язати з сервісом */ },
                modifier = Modifier.size(64.dp)
            ) {
                Text(text = "Prev")
            }

            // Кнопка Play / Pause
            FilledIconButton(
                onClick = { isPlaying = !isPlaying }, // Тимчасова логіка зміни іконки
                modifier = Modifier.size(80.dp)
            ) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            // Кнопка Next
            IconButton(
                onClick = { /* TODO: Зв'язати з сервісом */ },
                modifier = Modifier.size(64.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaPlayerScreenPreview() {
    ComposeTheme {
        MediaPlayerScreen()
    }
}
