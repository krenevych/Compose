package com.example.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MusicPlayerService: Service() {

    inner class LocalBinder: Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }


    // StateFlow для зберігання прогресу від 0.0 до 1.0
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var job : Job? = null

    override fun onDestroy() {
        super.onDestroy()

        coroutineScope.cancel()
    }

    ///////////////////////////////////////////
    ///////////////////////////////////////////

    // Захардкоджений список треків
    private val playlist = listOf(
        "Beethoven - Symphony No. 9",
        "Mozart - Eine kleine Nachtmusik",
        "Bach - Toccata and Fugue",
        "Vivaldi - The Four Seasons"
    )

    // Індекс поточного треку
    private var currentTrackIndex = 0

    // -----------------------------------------------------
    // Публічні методи служби, які буде викликати Activity
    // -----------------------------------------------------
    
    fun getCurrentTrack(): String {
        return playlist[currentTrackIndex]
    }

    private fun _start() {
        job = coroutineScope.launch {
            while (_progress.value < 1.0f){
                delay(500.milliseconds)
                _progress.value += 0.025f
                Log.d(TAG, "_start: ${_progress.value}")
            }
        }
    }

    fun playMusic(): String {
        Log.d(TAG, "Музика грає: ${getCurrentTrack()}")

        _start()

        return getCurrentTrack()
    }

    fun pauseMusic(): String  {
        Log.d(TAG, "Музика на паузі: ${getCurrentTrack()}")

        job?.cancel()
        job = null

        return getCurrentTrack()
    }
    
    fun nextTrack(): String {
        // Зациклюємо плейлист
        currentTrackIndex = (currentTrackIndex + 1) % playlist.size
        Log.d(TAG, "Наступний трек: ${getCurrentTrack()}")

        job?.cancel()
        _progress.value = 0.0f
        _start()

        return getCurrentTrack()
    }
    
    fun previousTrack(): String {
        // Зациклюємо плейлист
        currentTrackIndex = (currentTrackIndex + playlist.size - 1) % playlist.size
        Log.d(TAG, "Попередній трек: ${getCurrentTrack()}")

        job?.cancel()
        _progress.value = 0.0f
        _start()

        return getCurrentTrack()
    }

    companion object {
        val TAG = MusicPlayerService::class.java.simpleName
    }

}