package com.example.di.computer

import android.util.Log
import javax.inject.Inject

// Інʼєкція в конструктор - просто конструктор порожній
class Processor @Inject constructor() {

    val TAG = "Processor"
    fun start() {
        Log.d(TAG, "start: Процесор запущено")
    }
}