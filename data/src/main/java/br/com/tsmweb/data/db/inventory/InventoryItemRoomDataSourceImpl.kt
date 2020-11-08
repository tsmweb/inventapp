package br.com.tsmweb.data.db.inventory

import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.db.inventory.mapper.InventoryItemMapper
import br.com.tsmweb.data.inventory.source.InventoryItemRoomDataSource
import br.com.tsmweb.domain.inventory.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryItemRoomDataSourceImpl(
    db: AppDataBase
) : InventoryItemRoomDataSource {

    private val inventoryItemDao = db.inventoryItemDao()

    override fun loadInventoryItems(
        inventoryId: Long,
        status: Int,
        term: String
    ): Flow<List<InventoryItem>> {
        val term = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return inventoryItemDao.loadInventoryItems(inventoryId, status, term)
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