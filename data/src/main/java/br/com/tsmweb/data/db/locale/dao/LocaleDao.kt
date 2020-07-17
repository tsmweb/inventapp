package br.com.tsmweb.data.db.locale.dao

import androidx.room.*
import br.com.tsmweb.data.db.locale.entity.LocaleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocaleDao {

    @Query("""
        SELECT * FROM locale 
        WHERE (code LIKE :term OR name LIKE :term) 
        ORDER BY name, code
    """)
    fun loadLocales(term: String = "%"): Flow<List<LocaleEntity>>

    @Query("SELECT * FROM locale WHERE id = :id")
    fun loadLocale(id: String): Flow<LocaleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocale(locale: LocaleEntity)

    @Delete
    suspend fun removeLocale(locale: LocaleEntity)

}