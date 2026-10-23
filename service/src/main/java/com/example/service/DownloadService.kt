package com.example.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class DownloadService : Service() {

    private val TAG = DownloadService::class.java.simpleName  // "DownloadService"

    override fun onBind(intent: Intent?): IBinder? = null // для Foreground Service

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()

        startForeground(1, notification)

        // 3. Запускаємо нашу фонову роботу (Корутину)
        startDownloading()

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy: ")
    }

    private fun createNotification(): Notification {
        // Створення NotificationChannel для Android 8.0+ ...
        val channelId = "download_channel"

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Завантаження файлу")
            .setContentText("Файл завантажується у фоні...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    private fun startDownloading() {
        // ... Логіка завантаження через Coroutines (scope.launch) ...
        // Коли завантаження завершено, викликаємо stopSelf()
    }

}