package br.com.tsmweb.data.inventory.repository

import br.com.tsmweb.data.inventory.source.InventoryItemRoomDataSource
import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import kotlinx.coroutines.flow.Flow

class InventoryItemRepositoryImpl(
    private val inventoryItemRoomDataSource: InventoryItemRoomDataSource
) : InventoryItemRepository {

    override fun loadInventoryItems(
        inventoryId: Long,
        status: Int,
        term: String
    ): Flow<List<InventoryItem>> {
        return inventoryItemRoomDataSource.loadInventoryItems(inventoryId, status, term)
    }

    override fun loadInventoryItem(id: Long): Flow<InventoryItem?> {
        return inventoryItemRoomDataSource.loadInventoryItem(id)
    }

    override fun loadInventoryItemByBarcode(
        inventoryId: Long,
        barcode: String
    ): Flow<InventoryItem?> {
        return inventoryItemRoomDataSource.loadInventoryItemByBarcode(inventoryId, barcode)
    }

    override suspend fun saveInventoryItem(inventoryItem: InventoryItem) {
        inventoryItemRoomDataSource.saveInventoryItem(inventoryItem)
    }

    override suspend fun removeInventoryItemByInventory(inventoryId: Long) {
        inventoryItemRoomDataSource.removeInventoryItemByInventory(inventoryId)
    }

}