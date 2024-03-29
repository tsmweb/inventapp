package br.com.tsmweb.data.room.inventory

import androidx.room.withTransaction
import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.inventory.mapper.InventoryMapper
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import br.com.tsmweb.domain.inventory.model.Inventory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomInventoryRepository(
    private val db: AppDataBase
) : InventoryRepository {

    private val inventoryDao = db.inventoryDao()
    private val inventoryItemDao = db.inventoryItemDao()

    override fun loadInventories(localeId: String, term: String): Flow<List<Inventory>> {
        val tm = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return inventoryDao.loadInventories(localeId, tm)
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