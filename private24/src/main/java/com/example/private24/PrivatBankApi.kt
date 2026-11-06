package com.example.private24

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrivatBankApi {

    // GET https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11

    @GET("p24api/pubinfo?exchange")
    fun getCurrencyExchange(
        @Query("coursid")
        courseId: Int = 5
    ): Call<List<CurrencyItem>>

}