package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore
import kotlinx.coroutines.flow.Flow

class ListPatrimonyUseCase(
    private val patrimonyDataStore: PatrimonyDataStore
) {
    fun execute(localeId: String, term: String): Flow<List<Patrimony>> {
        return patrimonyDataStore.loadPatrimonies(localeId, term)
    }
}