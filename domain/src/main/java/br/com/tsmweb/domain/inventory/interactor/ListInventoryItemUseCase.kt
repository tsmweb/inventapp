package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import kotlinx.coroutines.flow.Flow

class ListInventoryItemUseCase(
    private val repository: InventoryItemRepository
) {
    fun execute(inventoryId: Long, status: StatusInventory, term: String): Flow<List<InventoryItem>> {
        return repository.loadInventoryItems(inventoryId, status.ordinal, term)
    }
}