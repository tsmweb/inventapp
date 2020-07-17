package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository

class RemovePatrimonyUseCase(
    private val repository: PatrimonyRepository
) {
    suspend fun execute(patrimonies: List<Patrimony>) {
        repository.removePatrimony(patrimonies)
    }
}