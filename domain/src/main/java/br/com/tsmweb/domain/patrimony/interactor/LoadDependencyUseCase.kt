package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore
import kotlinx.coroutines.flow.Flow

class LoadDependencyUseCase(
    private val patrimonyDataStore: PatrimonyDataStore
) {
    fun execute(localeId: String): Flow<List<String>> {
        return patrimonyDataStore.loadDependencies(localeId)
    }
}