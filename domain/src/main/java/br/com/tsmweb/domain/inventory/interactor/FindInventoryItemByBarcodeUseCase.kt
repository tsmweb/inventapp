package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import br.com.tsmweb.domain.patrimony.model.StatusPatrimony

class FindInventoryItemByBarcodeUseCase(
    private val repository: InventoryItemRepository
) {
    suspend fun execute(inventoryId: Long, barcode: String): InventoryItem {
        var inventoryItem = repository.loadInventoryItemByBarcode(inventoryId, barcode)

        if (inventoryItem == null) {
            inventoryItem = InventoryItem(
                id = 0,
                inventoryId = inventoryId,
                patrimonyCode = barcode,
                patrimonyName = barcode,
                patrimonyDependency = "-",
                patrimonyStatus = StatusPatrimony.ACTIVE,
                status = StatusInventory.NOT_FOUND,
                note = ""
            )
        }

        return inventoryItem
    }
}