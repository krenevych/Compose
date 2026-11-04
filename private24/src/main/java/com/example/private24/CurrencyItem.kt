package com.example.private24

import com.google.gson.annotations.SerializedName

data class CurrencyItem(
    @SerializedName("base_ccy")
    val baseCurrency: String,

    @SerializedName("ccy")
    val currency: String,

    val buy: String,

    val sale: String
)