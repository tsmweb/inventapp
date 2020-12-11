package br.com.tsmweb.data.room.patrimony.dao

import androidx.room.*
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyAndLocale
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyEntity
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

    @Transaction
    @Query("SELECT * FROM patrimony WHERE id = :id")
    fun loadPatrimony(id: Long): Flow<PatrimonyAndLocale>

    @Query("""
        SELECT DISTINCT dependency 
        FROM patrimony 
        WHERE locale_id = :localeId 
        ORDER BY dependency
    """)
    fun loadDependencies(localeId: String): Flow<List<String>>

    @Query("""
        SELECT p.*
        FROM patrimony p
        WHERE p.locale_id = :localeId
        AND p.code NOT IN (
            SELECT i.patrimony_code 
            FROM inventory_item i
            WHERE i.inventory_id = :inventoryId)
    """)
    suspend fun loadPatrimonyNotInInventoryItem(localeId: String, inventoryId: Long): List<PatrimonyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePatrimony(patrimony: PatrimonyEntity)

    @Delete
    suspend fun removePatrimony(vararg patrimony: PatrimonyEntity)

    @Query("DELETE FROM patrimony WHERE locale_id = :localeId")
    suspend fun removePatrimonyByLocale(localeId: String)
}