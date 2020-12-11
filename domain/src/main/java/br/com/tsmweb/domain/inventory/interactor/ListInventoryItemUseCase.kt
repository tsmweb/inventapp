package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.inventory.gateway.InventoryItemDataStore
import kotlinx.coroutines.flow.Flow

class ListInventoryItemUseCase(
    private val inventoryItemDataStore: InventoryItemDataStore
) {
    fun execute(inventoryId: Long, status: StatusInventory, term: String): Flow<List<InventoryItem>> {
        return inventoryItemDataStore.loadInventoryItems(inventoryId, status.ordinal, term)
    }
}