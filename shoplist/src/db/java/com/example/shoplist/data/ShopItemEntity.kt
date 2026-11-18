package com.example.shoplist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoplist.domain.ShopItem

@Entity(tableName = "shop_items")
internal data class ShopItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
) {
    fun toDomain() = ShopItem(id = id, name = name, count = count, enabled = enabled)
}

internal fun ShopItem.toEntity() =
    ShopItemEntity(id = id, name = name, count = count, enabled = enabled)
