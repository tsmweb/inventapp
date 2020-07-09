package br.com.tsmweb.data.inventory.source

import br.com.tsmweb.data.inventory.model.InventoryEntity
import kotlinx.coroutines.flow.Flow

interface InventoryRoomDataSource {
    fun loadInventories(term: String): Flow<List<InventoryEntity>>
    suspend fun createInventory(inventory: InventoryEntity)
    suspend fun removeInventory(inventories: List<InventoryEntity>)
    suspend fun populateInitialData()
}