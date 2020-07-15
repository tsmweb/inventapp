package br.com.tsmweb.data.place.repository

import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class PlaceRepositoryImpl(
    private val placeRoomDataSource: PlaceRoomDataSource
): PlaceRepository {

    override fun loadPlaces(term: String): Flow<List<Place>> {
        return placeRoomDataSource.loadPlaces(term)
    }

    override fun loadPlace(id: String): Flow<Place?> {
        return placeRoomDataSource.loadPlace(id)
    }

    override suspend fun savePlace(place: Place) {
        placeRoomDataSource.savePlace(place)
    }

    override suspend fun removePlace(place: Place) {
        placeRoomDataSource.removePlace(place)
    }

}