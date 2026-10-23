package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DownloadService : Service() {

    private val TAG = DownloadService::class.java.simpleName  // "DownloadService"
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

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
        createNotificationChannel() // Обов'язково викликати створення каналу перед нотифікацією
        Log.d(TAG, "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel() // Скасовуємо корутини при знищенні сервісу
        Log.d(TAG, "onDestroy: Сервіс зупинено")
    }

    private fun createNotificationChannel() {
        val channelId = "download_channel"
        val channelName = "Фонове завантаження"
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_LOW // Низький пріоритет, щоб не було зайвих звуків
        )
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val channelId = "download_channel"

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Завантаження файлу")
            .setContentText("Файл завантажується у фоні...")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Зазвичай використовують foreground іконку для статус-бару
            .build()
    }

    private fun startDownloading() {
        Log.d(TAG, "startDownloading: Початок завантаження")
        
        serviceScope.launch {
            for (i in 1..10) {
                delay(1000) // Симуляція довгої роботи (1 секунда на крок)
                Log.d(TAG, "startDownloading: Завантажено ${i * 10}%")
            }
            Log.d(TAG, "startDownloading: Завантаження завершено!")
            stopSelf() // Зупиняємо сервіс після успішного завершення роботи
        }
    }

}