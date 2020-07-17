package br.com.tsmweb.domain.patrimony.repository

import br.com.tsmweb.domain.patrimony.model.Patrimony
import kotlinx.coroutines.flow.Flow

interface PatrimonyRepository {
    fun loadPatrimonies(term: String): Flow<List<Patrimony>>
    fun loadPatrimony(id: Long): Flow<Patrimony?>
    suspend fun savePatrimony(patrimony: Patrimony)
    suspend fun removePatrimony(patrimonies: List<Patrimony>)
}