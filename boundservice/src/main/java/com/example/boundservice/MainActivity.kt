package com.example.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

//    val TAG = MainActivity::class.java.simpleName
    val TAG = "XXX"

    var musicService: MusicPlayerService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            serviceBinder: IBinder?
        ) {
            val binder = serviceBinder as MusicPlayerService.LocalBinder
            musicService = binder.getService()
            Log.d(TAG, "Службу підключено!")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MusicPlayerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        musicService = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {

                var currentTrack by remember { mutableStateOf("") }

                val progress by (musicService?.progress ?: MutableStateFlow(0f)).collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MediaPlayerScreen(
                        currentTrack = currentTrack,
                        progress = progress,
                        onPlayClick = {
                            currentTrack = musicService?.playMusic() ?: UNDEFINED_TRACK
                        },
                        onPauseClick = {
                            currentTrack = musicService?.pauseMusic() ?: UNDEFINED_TRACK
                        },
                        onNextTrack = {
                            currentTrack = musicService?.nextTrack() ?: UNDEFINED_TRACK
                        },
                        onPreviousTrack = {
                            currentTrack = musicService?.previousTrack() ?: UNDEFINED_TRACK
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    companion object {
        const val UNDEFINED_TRACK = "Unknown"
    }

}

@Composable
fun MediaPlayerScreen(
    modifier: Modifier = Modifier,
    currentTrack: String = "Unknown",
    progress: Float = 0f,
    onPlayClick: () -> Unit = {},
    onPauseClick: () -> Unit = {},
    onNextTrack: () -> Unit = {},
    onPreviousTrack: () -> Unit = {}
) {
    var isPlaying by remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(24.dp))

        // Смуга прогресу
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопки управління медіаплеєром
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Кнопка Previous
            IconButton(
                onClick = onPreviousTrack,
                modifier = Modifier.size(64.dp)
            ) {
                Text(text = "Prev")
            }

            // Кнопка Play / Pause
            FilledIconButton(
                onClick = {
                    if (isPlaying) {
                        onPauseClick()
                    } else {
                        onPlayClick()
                    }
                    isPlaying = !isPlaying
                },
                modifier = Modifier.size(80.dp)
            ) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            // Кнопка Next
            IconButton(
                onClick = onNextTrack,
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
