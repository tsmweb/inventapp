package br.com.tsmweb.data_room.place.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.tsmweb.data_room.place.entity.PlaceTable
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("""
        SELECT * FROM place 
        WHERE (code LIKE :term OR name LIKE :term) 
        ORDER BY code
    """)
    fun loadPlaces(term: String = "%"): Flow<List<PlaceTable>>
}