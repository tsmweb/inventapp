package br.com.tsmweb.data.inventory.repository

import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

class InventoryRepositoryImpl(
    private val inventoryRoomDataSource: InventoryRoomDataSource
): InventoryRepository {

    override fun loadInventories(localeId: String, term: String): Flow<List<Inventory>> {
        return inventoryRoomDataSource.loadInventories(localeId, term)
    }

    override fun loadInventory(id: Long): Flow<Inventory?> {
        return inventoryRoomDataSource.loadInventory(id)
    }

    override suspend fun saveInventory(inventory: Inventory): Long {
        return inventoryRoomDataSource.saveInventory(inventory)
    }

    override suspend fun removeInventory(inventories: List<Inventory>) {
        inventoryRoomDataSource.removeInventory(inventories)
    }

}