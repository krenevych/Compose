package com.example.shoplist.domain

data class ShopItem(
    val id: Int = 0,
    val name: String = "",
    val count: Int = 0,
    val enabled: Boolean = false
)
