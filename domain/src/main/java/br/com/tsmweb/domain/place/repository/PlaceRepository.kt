package br.com.tsmweb.domain.place.repository

import br.com.tsmweb.domain.place.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun loadPlaces(term: String): Flow<List<Place>>
    fun loadPlace(id: Long): Flow<Place?>
    suspend fun savePlace(place: Place)
    suspend fun removePlace(place: Place)
}