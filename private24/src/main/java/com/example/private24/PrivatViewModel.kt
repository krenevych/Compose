package com.example.private24

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class PrivatViewModel: ViewModel() {

//    private val TAG = PrivatViewModel::class.java.simpleName
    private val TAG = "XXXX"

    private val networkModule = PrivatBankNetworkModule()


    fun fetchRates() {

    }

    fun getCurrencyExchange(
        courseId: Int = 5,
        callback: (List<CurrencyItem>?) -> Unit
    ) {
        val call: Call<List<CurrencyItem>> = networkModule.service.getCurrencyExchange(courseId = courseId)
        call.enqueue(object : Callback<List<CurrencyItem>> {
            override fun onResponse(
                call: Call<List<CurrencyItem>?>,
                response: Response<List<CurrencyItem>?>
            ) {
                callback(response.body())
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(
                call: Call<List<CurrencyItem>?>,
                t: Throwable
            ) {
                Log.e(TAG, "onFailure: $t", )
                callback(null)
            }

        }
        )
    }
}