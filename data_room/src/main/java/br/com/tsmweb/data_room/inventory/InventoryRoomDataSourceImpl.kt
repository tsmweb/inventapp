package br.com.tsmweb.data_room.inventory

import br.com.tsmweb.data.inventory.model.InventoryEntity
import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.data_room.database.AppDataBase
import br.com.tsmweb.data_room.inventory.mapper.InventoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryRoomDataSourceImpl(
    db: AppDataBase
): InventoryRoomDataSource {

    private val inventoryDao = db.inventoryDao()

    override fun loadInventories(term: String): Flow<List<InventoryEntity>> {
        return inventoryDao.loadInventories(term)
            .map { inventories ->
                inventories.map(InventoryMapper::toEntity)
            }
    }

    override suspend fun createInventory(inventory: InventoryEntity) {
        inventoryDao.createInventory(InventoryMapper.fromEntity(inventory))
    }

    override suspend fun removeInventory(inventories: List<InventoryEntity>) {
        val tables = inventories.map(InventoryMapper::fromEntity)
        inventoryDao.removeInventory(*tables.toTypedArray())
    }
}