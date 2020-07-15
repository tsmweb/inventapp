package br.com.tsmweb.data.db.place

import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.data.db.place.mapper.PlaceMapper
import br.com.tsmweb.domain.place.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class PlaceRoomDataSourceImpl(
    db: AppDataBase
): PlaceRoomDataSource {

    private val placeDao = db.placeDao()

    override fun loadPlaces(term: String): Flow<List<Place>> {
        val term = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return placeDao.loadPlaces(term)
            .map { places -> places.map(PlaceMapper::toDomain) }
    }

    override fun loadPlace(id: String): Flow<Place?> {
        return placeDao.loadPlace(id)
            .map { placeEntity -> PlaceMapper.toDomain(placeEntity) }
    }

    override suspend fun savePlace(place: Place) {
        if (place.id.isBlank()) {
            place.id = UUID.randomUUID().toString()
        }

        placeDao.savePlace(PlaceMapper.fromDomain(place))
    }

    override suspend fun removePlace(place: Place) {
        placeDao.removePlace(PlaceMapper.fromDomain(place))
    }

}