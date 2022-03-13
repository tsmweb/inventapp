package br.com.tsmweb.data.room.patrimony.dao

import androidx.room.*
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyAndLocale
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatrimonyDao {

    @Query("""
        SELECT p.id,
            p.locale_id,
            p.code,
            p.name,
            p.dependency,
            p.status
        FROM patrimony p
        WHERE p.locale_id = :localeId  
        AND (p.code LIKE :term OR p.name LIKE :term)
        ORDER BY p.code, p.name
    """)
    fun loadPatrimonies(localeId: String, term: String): Flow<List<PatrimonyEntity>>

    @Transaction
    @Query("""
        SELECT p.id,
            p.locale_id,
            p.code,
            p.name,
            p.dependency,
            p.status
        FROM patrimony p 
        WHERE p.id = :id
        
    """)
    fun loadPatrimony(id: Long): Flow<PatrimonyAndLocale>

    @Query("""
        SELECT DISTINCT dependency 
        FROM patrimony 
        WHERE locale_id = :localeId 
        ORDER BY dependency
    """)
    fun loadDependencies(localeId: String): Flow<List<String>>

    @Query("""
        SELECT p.id,
            p.locale_id,
            p.code,
            p.name,
            p.dependency,
            p.status
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