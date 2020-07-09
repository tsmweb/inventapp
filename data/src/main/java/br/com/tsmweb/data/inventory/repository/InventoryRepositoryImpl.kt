package br.com.tsmweb.data.inventory.repository

import br.com.tsmweb.data.inventory.mapper.InventoryMapper
import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryRepositoryImpl(
    private val inventoryRoomDataSource: InventoryRoomDataSource
): InventoryRepository {

    override fun loadInventories(term: String): Flow<List<Inventory>> {
        return inventoryRoomDataSource.loadInventories(term).map { inventories ->
            inventories.map { inventory ->
                InventoryMapper.toDomain(inventory)
            }
        }
    }

    override suspend fun createInventory(inventory: Inventory) {
        inventoryRoomDataSource.createInventory(InventoryMapper.fromDomain(inventory))
    }

    override suspend fun removeInventory(inventories: List<Inventory>) {
        inventoryRoomDataSource.removeInventory(inventories.map { inventory ->
            InventoryMapper.fromDomain(inventory)
        })
    }

    override suspend fun populateInitialData() {
        inventoryRoomDataSource.populateInitialData()
    }

}