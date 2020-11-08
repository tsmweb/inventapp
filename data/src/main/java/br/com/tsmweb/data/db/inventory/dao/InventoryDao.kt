package br.com.tsmweb.data.db.inventory.dao

import androidx.room.*
import br.com.tsmweb.data.db.inventory.entity.InventoryEntity
import br.com.tsmweb.data.db.inventory.entity.InventoryView
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("""
        SELECT i.id,
            i.locale_id AS localeId,
            i.date_inventory AS dateInventory,
            COUNT(DISTINCT checked.id) AS patrimonyChecked,
            COUNT(DISTINCT uchecked.id) AS patrimonyUnchecked,
            COUNT(DISTINCT nfound.id) AS patrimonyNotFound
        FROM inventory i
        LEFT JOIN inventory_item checked ON checked.inventory_id = i.id AND checked.status = 0
        LEFT JOIN inventory_item uchecked ON uchecked.inventory_id = i.id AND uchecked.status = 1 
        LEFT JOIN inventory_item nfound ON nfound.inventory_id = i.id AND nfound.status = 2 
        WHERE i.locale_id = :localeId
        AND (i.date_inventory LIKE :term OR i.id LIKE :term)
        GROUP BY i.id, i.locale_id, i.date_inventory
        ORDER BY i.date_inventory DESC
    """)
    fun loadInventories(localeId: String, term: String): Flow<List<InventoryView>>

    @Query("""
        SELECT i.id,
            i.locale_id AS localeId,
            i.date_inventory AS dateInventory,
            COUNT(DISTINCT checked.id) AS patrimonyChecked,
            COUNT(DISTINCT uchecked.id) AS patrimonyUnchecked,
            COUNT(DISTINCT nfound.id) AS patrimonyNotFound
        FROM inventory i
        LEFT JOIN inventory_item checked ON checked.inventory_id = i.id AND checked.status = 0
        LEFT JOIN inventory_item uchecked ON uchecked.inventory_id = i.id AND uchecked.status = 1 
        LEFT JOIN inventory_item nfound ON nfound.inventory_id = i.id AND nfound.status = 2  
        WHERE i.id = :id
        GROUP BY i.id, i.locale_id, i.date_inventory
    """)
    fun loadInventory(id: Long): Flow<InventoryView>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInventory(inventory: InventoryEntity): Long

    @Delete
    suspend fun removeInventory(inventory: InventoryEntity)

    @Query("DELETE FROM inventory WHERE locale_id = :localeId")
    suspend fun removeInventoryByLocale(localeId: String)

}