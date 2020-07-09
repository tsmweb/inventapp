package br.com.tsmweb.domain.inventory.interactor

import br.com.tsmweb.domain.inventory.repository.InventoryRepository

class PopulateInitialUseCase(
    private val repository: InventoryRepository
) {

    suspend fun execute() {
        repository.populateInitialData()
    }

}