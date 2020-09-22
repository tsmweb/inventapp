package br.com.tsmweb.data.db.inventory

import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.db.inventory.mapper.InventoryMapper
import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.domain.inventory.model.Inventory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryRoomDataSourceImpl(
    db: AppDataBase
) : InventoryRoomDataSource {

    private val inventoryDao = db.inventoryDao()

    override fun loadInventories(localeId: String, term: String): Flow<List<Inventory>> {
        val term = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return inventoryDao.loadInventories(localeId, term)
            .map { inventories -> inventories.map(InventoryMapper::toDomain) }
    }

    override fun loadInventory(id: Long): Flow<Inventory?> {
        return inventoryDao.loadInventory(id)
            .map { entity -> InventoryMapper.toDomain(entity) }
    }

    override suspend fun saveInventory(inventory: Inventory) {
        inventoryDao.saveInventory(InventoryMapper.fromDomain(inventory))
    }

    override suspend fun removeInventory(inventories: List<Inventory>) {
        inventoryDao.removeInventory(*inventories.map(InventoryMapper::fromDomain).toTypedArray())
    }

}