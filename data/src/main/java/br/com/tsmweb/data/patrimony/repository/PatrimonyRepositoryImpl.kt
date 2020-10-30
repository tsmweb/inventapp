package br.com.tsmweb.data.patrimony.repository

import br.com.tsmweb.data.patrimony.source.PatrimonyRoomDataSource
import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import kotlinx.coroutines.flow.Flow

class PatrimonyRepositoryImpl(
    private val patrimonyRoomDataSource: PatrimonyRoomDataSource
): PatrimonyRepository {

    override fun loadPatrimonies(localeId: String, term: String): Flow<List<Patrimony>> {
        return patrimonyRoomDataSource.loadPatrimonies(localeId, term)
    }

    override fun loadPatrimony(id: Long): Flow<Patrimony?> {
        return patrimonyRoomDataSource.loadPatrimony(id)
    }

    override fun loadPatrimonyNotInInventoryItem(
        localeId: String,
        inventoryId: Long
    ): List<Patrimony> {
        return patrimonyRoomDataSource.loadPatrimonyNotInInventoryItem(localeId, inventoryId)
    }

    override suspend fun savePatrimony(patrimony: Patrimony) {
        patrimonyRoomDataSource.savePatrimony(patrimony)
    }

    override suspend fun removePatrimony(patrimonies: List<Patrimony>) {
        patrimonyRoomDataSource.removePatrimony(patrimonies)
    }

}