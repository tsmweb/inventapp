package br.com.tsmweb.data.place.repository

import br.com.tsmweb.data.place.mapper.PlaceMapper
import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaceRepositoryImpl(
    private val placeRoomDataSource: PlaceRoomDataSource
): PlaceRepository {

    override fun loadPlaces(term: String): Flow<List<Place>> {
        return placeRoomDataSource.loadPlaces(term).map { places ->
            places.map { place ->
                PlaceMapper.toDomain(place)
            }
        }
    }

}