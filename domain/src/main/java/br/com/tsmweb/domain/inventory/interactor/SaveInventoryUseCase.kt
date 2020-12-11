package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.gateway.InventoryDataStore
import java.lang.IllegalArgumentException

class SaveInventoryUseCase(
    private val inventoryDataStore: InventoryDataStore,
    private val createInventoryItemsUseCase: CreateInventoryItemsUseCase
) {
    suspend fun execute(inventory: Inventory) {
        if (inventoryIsValid(inventory)) {
            val id = inventoryDataStore.saveInventory(inventory)
            createInventoryItemsUseCase.execute(inventory.localeId, id)
        } else {
            throw IllegalArgumentException("Inventory is invalid")
        }
    }

    private fun inventoryIsValid(inventory: Inventory): Boolean {
        return inventory.localeId.isNotBlank()
    }
}