package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore

class RemovePatrimonyUseCase(
    private val patrimonyDataStore: PatrimonyDataStore
) {
    suspend fun execute(patrimonies: List<Patrimony>) {
        patrimonyDataStore.removePatrimony(patrimonies)
    }
}