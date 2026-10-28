package com.example.boundservice

import android.util.Log

class MusicPlayerService {

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

    fun playMusic(): String {
        Log.d(TAG, "Музика грає: ${getCurrentTrack()}")
        return getCurrentTrack()
    }

    fun pauseMusic(): String  {
        Log.d(TAG, "Музика на паузі: ${getCurrentTrack()}")
        return getCurrentTrack()
    }
    
    fun nextTrack(): String {
        // Зациклюємо плейлист
        currentTrackIndex = (currentTrackIndex + 1) % playlist.size
        Log.d(TAG, "Наступний трек: ${getCurrentTrack()}")
        return getCurrentTrack()
    }
    
    fun previousTrack(): String {
        // Зациклюємо плейлист
        currentTrackIndex = (currentTrackIndex + playlist.size - 1) % playlist.size
        Log.d(TAG, "Попередній трек: ${getCurrentTrack()}")
        return getCurrentTrack()
    }

    companion object {
        val TAG = MusicPlayerService::class.java.simpleName
    }

}