package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import br.com.tsmweb.domain.inventory.repository.InventoryRepository

class RemoveInventoryUseCase(
    private val inventoryRepository: InventoryRepository,
    private val inventoryItemRepository: InventoryItemRepository
) {
    suspend fun execute(inventories: List<Inventory>) {
        inventories.forEach {
            inventoryItemRepository.removeInventoryItemByInventory(it.id)
        }

        inventoryRepository.removeInventory(inventories)
    }
}