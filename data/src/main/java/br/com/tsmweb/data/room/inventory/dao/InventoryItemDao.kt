package br.com.tsmweb.data.room.inventory.dao

import androidx.room.*
import br.com.tsmweb.data.room.inventory.entity.InventoryItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {

    @Query("""
        SELECT i.id,
            i.inventory_id,
            i.patrimony_code,
            i.patrimony_name,
            i.patrimony_dependency,
            i.patrimony_status,
            i.status,
            i.note
        FROM inventory_item i
        WHERE i.inventory_id = :inventoryId
        AND i.status = :status
        AND (i.patrimony_code LIKE :term OR i.patrimony_name LIKE :term OR i.patrimony_dependency LIKE :term)
        ORDER BY i.inventory_id DESC
    """)
    fun loadInventoryItems(inventoryId: Long, status: Int, term: String): Flow<List<InventoryItemEntity>>

    @Query("""
        SELECT i.id,
            i.inventory_id,
            i.patrimony_code,
            i.patrimony_name,
            i.patrimony_dependency,
            i.patrimony_status,
            i.status,
            i.note
        FROM inventory_item i 
        WHERE i.id = :id
    """)
    fun loadInventoryItem(id: Long): Flow<InventoryItemEntity>

    @Query("""
        SELECT i.id,
            i.inventory_id,
            i.patrimony_code,
            i.patrimony_name,
            i.patrimony_dependency,
            i.patrimony_status,
            i.status,
            i.note
        FROM inventory_item i
        WHERE i.inventory_id = :inventoryId
        AND i.patrimony_code = :barcode
    """)
    suspend fun loadInventoryItemByBarcode(inventoryId: Long, barcode: String): InventoryItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInventoryItem(inventoryItem: InventoryItemEntity)

    @Query("DELETE FROM inventory_item WHERE inventory_id = :inventoryId")
    suspend fun removeInventoryItemByInventory(inventoryId: Long)

    @Query("""
        DELETE FROM inventory_item
        WHERE inventory_id IN (
            SELECT id
            FROM inventory
            WHERE locale_id = :localeId
        )
    """)
    suspend fun removeInventoryItemByLocale(localeId: String)

}