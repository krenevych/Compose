package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    private val TAG = "XXXXX"

    override suspend fun doWork(): Result {
        Log.d(TAG, "Починаємо синхронізацію...")

        delay(5_000.milliseconds)

        Log.d(TAG, "Синхронізація успішна!")

        return Result.success()
    }
}