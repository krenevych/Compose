package com.example.di.computer

import android.util.Log

// Інʼєкція в конструктор - просто конструктор порожній
class Processor {

    val TAG = "Processor"
    fun start() {
        Log.d(TAG, "start: Процесор запущено")
    }
}