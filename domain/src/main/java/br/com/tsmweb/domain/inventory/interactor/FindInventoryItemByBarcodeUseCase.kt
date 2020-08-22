package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import kotlinx.coroutines.flow.Flow

class FindInventoryItemByBarcodeUseCase(
    private val repository: InventoryItemRepository
) {
    fun execute(inventoryId: Long, barcode: String): Flow<InventoryItem?> {
        return repository.loadInventoryItemByBarcode(inventoryId, barcode)
    }
}