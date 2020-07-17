package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import kotlinx.coroutines.flow.Flow

class ViewPatrimonyDetailsUseCase(
    private val repository: PatrimonyRepository
) {
    fun execute(id: Long): Flow<Patrimony?> {
        return repository.loadPatrimony(id)
    }
}