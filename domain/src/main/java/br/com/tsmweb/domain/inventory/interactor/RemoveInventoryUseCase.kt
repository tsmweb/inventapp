package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.gateway.InventoryDataStore

class RemoveInventoryUseCase(
    private val inventoryDataStore: InventoryDataStore
) {
    suspend fun execute(inventories: List<Inventory>) {
        inventoryDataStore.removeInventory(inventories)
    }
}