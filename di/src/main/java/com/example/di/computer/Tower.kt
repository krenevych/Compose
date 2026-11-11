package com.example.di.computer

import android.util.Log
import javax.inject.Inject

class Tower @Inject constructor(){

    val TAG = "Tower"

    fun start() {
        Log.d(TAG, "start: Системник підʼєднано")
    }
}
