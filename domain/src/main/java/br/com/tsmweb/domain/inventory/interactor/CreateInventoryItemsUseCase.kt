package br.com.tsmweb.domain.inventory.interactor;

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.inventory.gateway.InventoryItemDataStore;
import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore

class CreateInventoryItemsUseCase(
    private val inventoryItemDataStore: InventoryItemDataStore,
    private val patrimonyDataStore: PatrimonyDataStore
) {
    suspend fun execute(localeId: String, inventoryId: Long) {
        patrimonyDataStore.loadPatrimonyNotInInventoryItem(localeId, inventoryId)
            .map { patrimony ->
                InventoryItem(
                    id = 0,
                    inventoryId = inventoryId,
                    patrimonyCode = patrimony.code,
                    patrimonyName = patrimony.name,
                    patrimonyDependency = patrimony.dependency,
                    patrimonyStatus = patrimony.status,
                    status = StatusInventory.UNCHECKED,
                    note = ""
                )
            }
            .forEach { inventoryItemDataStore.saveInventoryItem(it) }
    }
}
