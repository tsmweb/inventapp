package br.com.tsmweb.domain.inventory.repository

import br.com.tsmweb.domain.inventory.model.InventoryItem
import kotlinx.coroutines.flow.Flow

interface InventoryItemRepository {
    fun loadInventoryItems(inventoryId: Long, status: Int, term: String): Flow<List<InventoryItem>>
    fun loadInventoryItem(id: Long): Flow<InventoryItem?>
    suspend fun loadInventoryItemByBarcode(inventoryId: Long, barcode: String): InventoryItem?
    suspend fun saveInventoryItem(inventoryItem: InventoryItem)
}