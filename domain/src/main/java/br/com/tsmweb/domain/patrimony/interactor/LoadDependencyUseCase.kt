package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import kotlinx.coroutines.flow.Flow

class LoadDependencyUseCase(
    private val repository: PatrimonyRepository
) {
    fun execute(localeId: String): Flow<List<String>> {
        return repository.loadDependencies(localeId)
    }
}