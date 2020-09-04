package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import java.lang.IllegalArgumentException

class SaveInventoryUseCase(
    private val repository: InventoryRepository
) {
    suspend fun execute(inventory: Inventory) {
        if (inventoryIsValid(inventory)) {
            repository.saveInventory(inventory)
        } else {
            throw IllegalArgumentException("Inventory is invalid")
        }
    }

    private fun inventoryIsValid(inventory: Inventory): Boolean {
        return inventory.locale.id.isNotBlank()
    }
}