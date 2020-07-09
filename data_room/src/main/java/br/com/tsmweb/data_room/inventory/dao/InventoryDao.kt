package br.com.tsmweb.data_room.inventory.dao

import androidx.room.*
import br.com.tsmweb.data_room.inventory.entity.InventoryTable
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("""
        SELECT * FROM inventory 
        WHERE (place_code LIKE :term OR place_name LIKE :term) 
        ORDER BY date_inventory DESC
    """)
    fun loadInventories(term: String = "%"): Flow<List<InventoryTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createInventory(inventory: InventoryTable)

    @Delete
    suspend fun removeInventory(vararg inventory: InventoryTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveInventory(inventory: InventoryTable)
}