package br.com.tsmweb.data.inventory.source

import br.com.tsmweb.domain.inventory.model.Inventory
import kotlinx.coroutines.flow.Flow

interface InventoryRoomDataSource {
    fun loadInventories(localeId: String, term: String): Flow<List<Inventory>>
    fun loadInventory(id: Long): Flow<Inventory?>
    suspend fun saveInventory(inventory: Inventory): Long
    suspend fun removeInventory(inventories: List<Inventory>)
}