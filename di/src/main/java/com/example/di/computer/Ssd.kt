package com.example.di.computer

import android.util.Log
import javax.inject.Inject

interface Storage{
    fun readBootData()
}

class Ssd @Inject constructor() : Storage {

    val TAG = "Ssd"

    override fun readBootData() {
        Log.d(TAG, "readBootData: Операційна система з SSD")
    }

}

class Hdd @Inject constructor() : Storage {
    val TAG = "Hdd"

    override fun readBootData() {
        Log.d(TAG, "readBootData: Операційна система з HDD")
    }
}