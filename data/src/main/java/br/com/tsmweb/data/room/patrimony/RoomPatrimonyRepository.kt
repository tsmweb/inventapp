package br.com.tsmweb.data.room.patrimony

import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.patrimony.mapper.PatrimonyMapper
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import br.com.tsmweb.domain.patrimony.model.Patrimony
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomPatrimonyRepository(
    db: AppDataBase
): PatrimonyRepository {

    private val patrimonyDao = db.patrimonyDao()

    override fun loadPatrimonies(localeId: String, term: String): Flow<List<Patrimony>> {
        val tm = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return patrimonyDao.loadPatrimonies(localeId, tm)
            .map { patrimonies -> patrimonies.map(PatrimonyMapper::toDomain)}
    }

    override fun loadPatrimony(id: Long): Flow<Patrimony?> {
        return patrimonyDao.loadPatrimony(id)
            .map { entity -> PatrimonyMapper.toDomain(entity) }
    }

    override fun loadDependencies(localeId: String): Flow<List<String>> {
        return patrimonyDao.loadDependencies(localeId)
    }

    override suspend fun loadPatrimonyNotInInventoryItem(
        localeId: String,
        inventoryId: Long
    ): List<Patrimony> {
        return patrimonyDao.loadPatrimonyNotInInventoryItem(localeId, inventoryId)
            .map { entity -> PatrimonyMapper.toDomain(entity) }
    }

    override suspend fun savePatrimony(patrimony: Patrimony) {
        patrimonyDao.savePatrimony(PatrimonyMapper.fromDomain(patrimony))
    }

    override suspend fun removePatrimony(patrimonies: List<Patrimony>) {
        patrimonyDao.removePatrimony(*patrimonies.map(PatrimonyMapper::fromDomain).toTypedArray())
    }

}