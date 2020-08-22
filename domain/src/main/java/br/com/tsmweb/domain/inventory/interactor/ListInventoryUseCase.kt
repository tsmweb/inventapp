package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

class ListInventoryUseCase(
    private val repository: InventoryRepository
) {
    fun execute(localeId: String, term: String): Flow<List<Inventory>> {
        return repository.loadInventories(localeId, term)
    }
}