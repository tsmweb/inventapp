package br.com.tsmweb.data.db.inventory.dao

import androidx.room.*
import br.com.tsmweb.data.db.inventory.entity.InventoryItemAndPatrimony
import br.com.tsmweb.data.db.inventory.entity.InventoryItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {

    @Transaction
    @Query("""
        SELECT vi.*
        FROM inventory_item vi
        INNER JOIN patrimony p ON p.id = vi.patrimony_id
        WHERE vi.inventory_id = :inventoryId
        AND vi.status = :status
        AND (p.code LIKE :term OR p.name LIKE :term)
        ORDER BY p.code, p.name
    """)
    fun loadInventoryItems(inventoryId: Long, status: Int, term: String): Flow<List<InventoryItemAndPatrimony>>

    @Transaction
    @Query("SELECT * FROM inventory_item WHERE id = :id")
    fun loadInventoryItem(id: Long): Flow<InventoryItemAndPatrimony>

    @Transaction
    @Query("""
        SELECT vi.* 
        FROM inventory_item vi 
        INNER JOIN patrimony p ON p.id = vi.patrimony_id
        WHERE vi.inventory_id = :inventoryId
        AND p.code = :barcode
    """)
    fun loadInventoryItemByBarcode(inventoryId: Long, barcode: String): Flow<InventoryItemAndPatrimony>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInventoryItem(inventoryItem: InventoryItemEntity)

    @Delete
    suspend fun removeInventoryItem(vararg inventoryItem: InventoryItemEntity)

}