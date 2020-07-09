package br.com.tsmweb.data.db.place.dao

import androidx.room.*
import br.com.tsmweb.data.db.place.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("""
        SELECT * FROM place 
        WHERE (code LIKE :term OR name LIKE :term) 
        ORDER BY code
    """)
    fun loadPlaces(term: String = "%"): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM place WHERE id = :id")
    fun loadPlace(id: Long): Flow<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlace(place: PlaceEntity)

    @Delete
    fun removePlace(place: PlaceEntity)

}