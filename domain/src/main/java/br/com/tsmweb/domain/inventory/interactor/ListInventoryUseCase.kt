package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.gateway.InventoryDataStore
import kotlinx.coroutines.flow.Flow

class ListInventoryUseCase(
    private val inventoryDataStore: InventoryDataStore
) {
    fun execute(localeId: String, term: String): Flow<List<Inventory>> {
        return inventoryDataStore.loadInventories(localeId, term)
    }
}