package com.example.broadcastflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BroadcastTracker(context: Context) {

    val tracker: Flow<String> = callbackFlow {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {

                intent?.action?.let { action: String ->
                    Log.d(TAG, "action: $action")
                    trySend(action)
                }

            }

        }

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(ACTION_BROADCAST_1)
            addAction(ACTION_BROADCAST_2)
        }

        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_EXPORTED  // TODO: for debug. For release RECEIVER_NOT_EXPORTED
        )

        trySend("Undef action")

        awaitClose {
            context.unregisterReceiver(receiver)
        }

    }

    companion object {
        const val TAG = "BroadcastTracker"
    }

}