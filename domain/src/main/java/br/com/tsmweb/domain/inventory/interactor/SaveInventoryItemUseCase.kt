package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.inventory.gateway.InventoryItemDataStore
import java.lang.IllegalArgumentException

class SaveInventoryItemUseCase(
    private val inventoryItemDataStore: InventoryItemDataStore
) {
    suspend fun execute(inventoryItem: InventoryItem, editMode: Boolean) {
        if (inventoryItemIsValid(inventoryItem)) {
            if (!editMode && inventoryItem.status == StatusInventory.UNCHECKED) {
                inventoryItem.status = StatusInventory.CHECKED
            }

            inventoryItemDataStore.saveInventoryItem(inventoryItem)
        } else {
            throw IllegalArgumentException("InventoryItem is invalid")
        }
    }

    private fun inventoryItemIsValid(inventoryItem: InventoryItem): Boolean {
        return (inventoryItem.inventoryId > 0 &&
                inventoryItem.patrimonyCode.isNotBlank() &&
                inventoryItem.patrimonyCode.isNotEmpty() &&
                inventoryItem.patrimonyName.isNotBlank() &&
                inventoryItem.patrimonyName.isNotEmpty() &&
                inventoryItem.patrimonyDependency.isNotBlank() &&
                inventoryItem.patrimonyDependency.isNotEmpty())
    }
}