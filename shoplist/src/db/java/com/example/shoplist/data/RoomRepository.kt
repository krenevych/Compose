package com.example.shoplist.data

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomRepository(private val dao: ShopItemDao) : Repository {
    override val shopList: Flow<List<ShopItem>> = dao.observeAll().map { items ->
        items.map(ShopItemEntity::toDomain)
    }

    override suspend fun add(item: ShopItem) {
        dao.insert(item.copy(id = 0).toEntity())
    }

    override suspend fun edit(item: ShopItem) {
        if (dao.update(item.toEntity()) == 0) {
            throw NoSuchElementException("Shop item with id ${item.id} not found")
        }
    }

    override suspend fun delete(item: ShopItem) {
        dao.deleteById(item.id)
    }

    override suspend fun getShopItemById(id: Int): ShopItem {
        return dao.findById(id)?.toDomain()
            ?: throw NoSuchElementException("Shop item with id $id not found")
    }
}
