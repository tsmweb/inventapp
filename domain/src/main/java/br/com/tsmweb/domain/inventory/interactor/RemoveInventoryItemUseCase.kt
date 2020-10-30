package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository

class RemoveInventoryItemUseCase(
    private val repository: InventoryItemRepository
) {
    suspend fun execute(inventoryItems: List<InventoryItem>) {
        repository.removeInventoryItem(inventoryItems)
    }
}