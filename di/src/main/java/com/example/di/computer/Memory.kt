package com.example.di.computer

import android.util.Log
import javax.inject.Inject

class Memory @Inject constructor(){

    val TAG = "Memory"
    fun initialize() {
        Log.d(TAG, "initialize: Оперативну пам’ять підготовлено")
    }
}