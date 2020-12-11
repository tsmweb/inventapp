package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore
import kotlinx.coroutines.flow.Flow

class DetailsPatrimonyUseCase(
    private val patrimonyDataStore: PatrimonyDataStore
) {
    fun execute(id: Long): Flow<Patrimony?> {
        return patrimonyDataStore.loadPatrimony(id)
    }
}