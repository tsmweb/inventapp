package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository

class RemoveInventoryUseCase(
    private val repository: InventoryRepository
) {
    suspend fun execute(inventories: List<Inventory>) {
        repository.removeInventory(inventories)
    }
}