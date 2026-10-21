package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        // 1. Отримуємо Action події
        val action = intent?.action

        if (action == Intent.ACTION_BATTERY_LOW){
            Toast.makeText(context, "Low battery", Toast.LENGTH_SHORT).show()
        } else if (action == Intent.ACTION_AIRPLANE_MODE_CHANGED){
            val airpane_mode = intent.getBooleanExtra("state", false)
            Toast.makeText(context, "airplane mode = $airpane_mode", Toast.LENGTH_SHORT).show()
        }


        // 2. Виконуємо необхідну логіку
        Log.d("BR", "Отримано бродкаст із акшеном: $action")

    }
}