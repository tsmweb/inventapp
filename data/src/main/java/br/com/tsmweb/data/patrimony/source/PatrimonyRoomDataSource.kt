package br.com.tsmweb.data.patrimony.source

import br.com.tsmweb.domain.patrimony.model.Patrimony
import kotlinx.coroutines.flow.Flow

interface PatrimonyRoomDataSource {
    fun loadPatrimonies(localeId: String, term: String): Flow<List<Patrimony>>
    fun loadPatrimony(id: Long): Flow<Patrimony?>
    fun loadDependencies(localeId: String): Flow<List<String>>
    suspend fun loadPatrimonyNotInInventoryItem(localeId: String, inventoryId: Long): List<Patrimony>
    suspend fun savePatrimony(patrimony: Patrimony)
    suspend fun removePatrimony(patrimonies: List<Patrimony>)
}