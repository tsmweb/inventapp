package br.com.tsmweb.data.place.source

import br.com.tsmweb.data.place.model.PlaceEntity
import kotlinx.coroutines.flow.Flow

interface PlaceRoomDataSource {
    fun loadPlaces(term: String): Flow<List<PlaceEntity>>
}