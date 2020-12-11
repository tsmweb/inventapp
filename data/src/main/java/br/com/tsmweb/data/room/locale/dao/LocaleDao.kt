package br.com.tsmweb.data.room.locale.dao

import androidx.room.*
import br.com.tsmweb.data.room.locale.entity.LocaleEntity
import br.com.tsmweb.data.room.locale.entity.LocaleView
import kotlinx.coroutines.flow.Flow

@Dao
interface LocaleDao {

    @Query("""
        SELECT l.*, 
            COUNT(p.id) AS amountPatrimony, 
            IFNULL(
                (SELECT MAX(IFNULL(date_inventory, '1900-01-01')) 
                 FROM inventory 
                 WHERE locale_id = l.id), 
                '1900-01-01'
            ) AS lastInventory   
        FROM locale l
        LEFT JOIN patrimony p ON p.locale_id = l.id
        WHERE (l.code LIKE :term OR l.name LIKE :term)
        GROUP BY l.id
        ORDER BY l.name, l.code
    """)
    fun loadLocales(term: String = "%"): Flow<List<LocaleView>>

    @Query("SELECT * FROM locale WHERE id = :id")
    fun loadLocale(id: String): Flow<LocaleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocale(locale: LocaleEntity)

    @Delete
    suspend fun removeLocale(locale: LocaleEntity)

}