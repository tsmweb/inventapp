package br.com.tsmweb.data.place.source

import br.com.tsmweb.domain.place.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRoomDataSource {
    fun loadPlaces(term: String): Flow<List<Place>>
    fun loadPlace(id: String): Flow<Place?>
    suspend fun savePlace(place: Place)
    suspend fun removePlace(place: Place)
}