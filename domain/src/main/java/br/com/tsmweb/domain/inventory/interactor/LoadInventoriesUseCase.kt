package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

class LoadInventoriesUseCase(
    private val repository: InventoryRepository
) {
    fun execute(term: String): Flow<List<Inventory>> {
        return repository.loadInventories(term)
    }
}