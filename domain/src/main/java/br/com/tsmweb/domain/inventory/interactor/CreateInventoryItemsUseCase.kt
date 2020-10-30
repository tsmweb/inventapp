package br.com.tsmweb.domain.inventory.interactor;

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository;
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository

class CreateInventoryItemsUseCase(
        private val inventoryItemRepository: InventoryItemRepository,
        private val patrimonyRepository: PatrimonyRepository
) {
    suspend fun execute(localeId: String, inventoryId: Long) {
        patrimonyRepository.loadPatrimonyNotInInventoryItem(localeId, inventoryId)
            .map { patrimony ->
                InventoryItem(
                        id = 0,
                        inventoryId = inventoryId,
                        patrimony = patrimony,
                        status = StatusInventory.UNCHECKED,
                        note = ""
                )
            }
            .forEach { inventoryItemRepository.saveInventoryItem(it) }
    }
}
