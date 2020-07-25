package br.com.tsmweb.data.db.patrimony.dao

import androidx.room.*
import br.com.tsmweb.data.db.patrimony.entity.PatrimonyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatrimonyDao {

    @Query("""
        SELECT * FROM patrimony
        WHERE locale_id = :localeId  
        AND (code LIKE :term OR name LIKE :term)
        ORDER BY code, name
    """)
    fun loadPatrimonies(localeId: String, term: String): Flow<List<PatrimonyEntity>>

    @Query("SELECT * FROM patrimony WHERE id = :id")
    fun loadPatrimony(id: Long): Flow<PatrimonyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePatrimony(patrimony: PatrimonyEntity)

    @Delete
    suspend fun removePatrimony(vararg patrimony: PatrimonyEntity)
}