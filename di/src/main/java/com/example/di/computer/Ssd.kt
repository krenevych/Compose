package com.example.di.computer

import android.util.Log
import javax.inject.Inject

class Ssd @Inject constructor(){

    val TAG = "Ssd"

    fun readBootData() {
        Log.d(TAG, "readBootData: Операційна система з SSD")
    }

}