package br.com.tsmweb.data.patrimony.repository

import br.com.tsmweb.data.patrimony.source.PatrimonyRoomDataSource
import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import kotlinx.coroutines.flow.Flow

class PatrimonyRepository(
    private val patrimonyRoomDataSource: PatrimonyRoomDataSource
): PatrimonyRepository {

    override fun loadPatrimonies(term: String): Flow<List<Patrimony>> {
        return patrimonyRoomDataSource.loadPatrimonies(term)
    }

    override fun loadPatrimony(id: Long): Flow<Patrimony?> {
        return patrimonyRoomDataSource.loadPatrimony(id)
    }

    override suspend fun savePatrimony(patrimony: Patrimony) {
        patrimonyRoomDataSource.savePatrimony(patrimony)
    }

    override suspend fun removePatrimony(patrimonies: List<Patrimony>) {
        patrimonyRoomDataSource.removePatrimony(patrimonies)
    }

}