package br.com.tsmweb.data.db.inventory.dao

import androidx.room.*
import br.com.tsmweb.data.db.inventory.entity.InventoryAndLocale
import br.com.tsmweb.data.db.inventory.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Transaction
    @Query("""
        SELECT * FROM inventory
        WHERE locale_id = :localeId  
        AND (date_inventory LIKE :term OR id LIKE :term)
        ORDER BY date_inventory DESC
    """)
    fun loadInventories(localeId: String, term: String): Flow<List<InventoryAndLocale>>

    @Transaction
    @Query("SELECT * FROM inventory WHERE id = :id")
    fun loadInventory(id: Long): Flow<InventoryAndLocale>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInventory(inventory: InventoryEntity)

    @Delete
    suspend fun removeInventory(vararg inventory: InventoryEntity)

}