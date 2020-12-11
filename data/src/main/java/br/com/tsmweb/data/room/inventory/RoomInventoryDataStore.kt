package br.com.tsmweb.data.room.inventory

import androidx.room.withTransaction
import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.inventory.mapper.InventoryMapper
import br.com.tsmweb.domain.inventory.gateway.InventoryDataStore
import br.com.tsmweb.domain.inventory.model.Inventory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomInventoryDataStore(
    private val db: AppDataBase
) : InventoryDataStore {

    private val inventoryDao = db.inventoryDao()
    private val inventoryItemDao = db.inventoryItemDao()

    override fun loadInventories(localeId: String, term: String): Flow<List<Inventory>> {
        val term = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return inventoryDao.loadInventories(localeId, term)
            .map { inventories -> inventories.map(InventoryMapper::toDomain) }
    }

    override fun loadInventory(id: Long): Flow<Inventory?> {
        return inventoryDao.loadInventory(id)
            .map { entity -> InventoryMapper.toDomain(entity) }
    }

    override suspend fun saveInventory(inventory: Inventory): Long {
        return inventoryDao.saveInventory(InventoryMapper.fromDomain(inventory))
    }

    override suspend fun removeInventory(inventories: List<Inventory>) {
        inventories.forEach {
            removeInventory(it)
        }
    }

    private suspend fun removeInventory(inventory: Inventory) {
        db.withTransaction {
            inventoryItemDao.removeInventoryItemByInventory(inventory.id)
            inventoryDao.removeInventory(InventoryMapper.fromDomain(inventory))
        }
    }

}