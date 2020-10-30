package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import java.lang.IllegalArgumentException

class SaveInventoryUseCase(
    private val repository: InventoryRepository,
    private val createInventoryItemsUseCase: CreateInventoryItemsUseCase
) {
    suspend fun execute(inventory: Inventory) {
        if (inventoryIsValid(inventory)) {
            val id = repository.saveInventory(inventory)
            createInventoryItemsUseCase.execute(inventory.localeId, id)
        } else {
            throw IllegalArgumentException("Inventory is invalid")
        }
    }

    private fun inventoryIsValid(inventory: Inventory): Boolean {
        return inventory.localeId.isNotBlank()
    }
}