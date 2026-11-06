package com.example.roomdb

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "card_table")
data class CardData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "Title")
    val title: String = "Заголовок картки",

    @ColumnInfo(name = "Description")
    val description: String = "Це дуже детальний опис для цієї картки, який з'являється після того, як ви натиснете кнопку розгортання. Тут може бути багато тексту та іншої корисної інформації.",

    @ColumnInfo(name = "Creation time")
    val creationTime: String = "2023-10-27 15:30"
)
