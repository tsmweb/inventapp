package br.com.tsmweb.data.room.inventory

import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.inventory.mapper.InventoryItemMapper
import br.com.tsmweb.domain.inventory.gateway.InventoryItemDataStore
import br.com.tsmweb.domain.inventory.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomInventoryItemDataStore(
    db: AppDataBase
) : InventoryItemDataStore {

    private val inventoryItemDao = db.inventoryItemDao()

    override fun loadInventoryItems(
        inventoryId: Long,
        status: Int,
        term: String
    ): Flow<List<InventoryItem>> {
        val tm = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return inventoryItemDao.loadInventoryItems(inventoryId, status, tm)
            .map { items -> items.map(InventoryItemMapper::toDomain) }
    }

    override fun loadInventoryItem(id: Long): Flow<InventoryItem?> {
        return inventoryItemDao.loadInventoryItem(id)
            .map { entity -> InventoryItemMapper.toDomain(entity) }
    }

    override suspend fun loadInventoryItemByBarcode(
        inventoryId: Long,
        barcode: String
    ): InventoryItem? {
        inventoryItemDao.loadInventoryItemByBarcode(inventoryId, barcode)?.let {
            return InventoryItemMapper.toDomain(it)
        }

        return null
    }

    override suspend fun saveInventoryItem(inventoryItem: InventoryItem) {
        inventoryItemDao.saveInventoryItem(InventoryItemMapper.fromDomain(inventoryItem))
    }

}