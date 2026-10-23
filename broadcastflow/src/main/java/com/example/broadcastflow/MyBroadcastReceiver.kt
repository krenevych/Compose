package com.example.broadcastflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        // 1. Отримуємо Action події
        val action = intent?.action

        when (action) {
            Intent.ACTION_BATTERY_LOW -> {
                Log.d(TAG, "MyBroadcastReceiver.onReceive: action: $action")
                Toast.makeText(context, "Low battery", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                Log.d(TAG, "MyBroadcastReceiver.onReceive: action: $action")
                val airpane_mode = intent.getBooleanExtra("state", false)
                Toast.makeText(context, "airplane mode = $airpane_mode", Toast.LENGTH_SHORT).show()
            }
            ACTION_BROADCAST_1 -> {
                Log.d(TAG, "MyBroadcastReceiver.onReceive: action: $action")
            }
            ACTION_BROADCAST_2 -> {
                Log.d(TAG, "MyBroadcastReceiver.onReceive: action: $action")
            }
        }

    }

    companion object {
        const val TAG = "MyBroadcastReceiver"
    }

}