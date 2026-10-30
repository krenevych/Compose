package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    private val TAG = "XXXXX"

    override suspend fun doWork(): Result {

        val url = inputData.getString("server_url") ?: return Result.failure()
        val someting = inputData.getBoolean("something", false)


        Log.d(TAG, "Починаємо синхронізацію з сервером $url ...")
        var i = 0
        while (i <= 100){
            delay(500.milliseconds)
            i += 10

            Log.d(TAG, "Cинхронізовано $i%")

            val outputDataBuilder = Data.Builder()
            outputDataBuilder.putInt("synchronized", i)
            val outputData = outputDataBuilder.build()
            Result.success(outputData)

        }


        Log.d(TAG, "Синхронізація успішна!")

        return Result.success()
    }
}