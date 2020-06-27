package br.com.tsmweb.domain.inventory.repository

import br.com.tsmweb.domain.inventory.model.Inventory
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun loadInventories(term: String): Flow<List<Inventory>>
    suspend fun createInventory(inventory: Inventory)
    suspend fun removeInventory(inventories: List<Inventory>)
}