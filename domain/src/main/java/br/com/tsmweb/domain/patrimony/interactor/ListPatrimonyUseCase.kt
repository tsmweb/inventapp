package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import kotlinx.coroutines.flow.Flow

class ListPatrimonyUseCase(
    private val repository: PatrimonyRepository
) {
    fun execute(localeId: String, term: String): Flow<List<Patrimony>> {
        return repository.loadPatrimonies(localeId, term)
    }
}