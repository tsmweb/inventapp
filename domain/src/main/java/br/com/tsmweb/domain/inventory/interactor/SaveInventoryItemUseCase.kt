package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import java.lang.IllegalArgumentException

class SaveInventoryItemUseCase(
    private val repository: InventoryItemRepository
) {
    suspend fun execute(inventoryItem: InventoryItem) {
        if (inventoryItemIsValid(inventoryItem)) {
            repository.saveInventoryItem(inventoryItem)
        } else {
            throw IllegalArgumentException("InventoryItem is invalid")
        }
    }

    private fun inventoryItemIsValid(inventoryItem: InventoryItem): Boolean {
        return (inventoryItem.inventoryId > 0 &&
                inventoryItem.patrimonyId > 0 &&
                inventoryItem.patrimonyCode.isNotBlank() &&
                inventoryItem.patrimonyName.isNotBlank() &&
                inventoryItem.patrimonyDependency.isNotBlank() &&
                inventoryItem.patrimonyStatus != null &&
                inventoryItem.status != null)
    }
}