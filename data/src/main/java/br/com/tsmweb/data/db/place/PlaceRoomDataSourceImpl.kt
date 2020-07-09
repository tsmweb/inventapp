package br.com.tsmweb.data.db.place

import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.data.db.place.mapper.PlaceMapper
import br.com.tsmweb.domain.place.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaceRoomDataSourceImpl(
    db: AppDataBase
): PlaceRoomDataSource {

    private val placeDao = db.placeDao()

    override fun loadPlaces(term: String): Flow<List<Place>> {
        return placeDao.loadPlaces(term)
            .map { places -> places.map(PlaceMapper::toDomain) }
    }

    override fun loadPlace(id: Long): Flow<Place?> {
        return placeDao.loadPlace(id)
            .map { placeEntity -> PlaceMapper.toDomain(placeEntity) }
    }

    override suspend fun savePlace(place: Place) {
        placeDao.savePlace(PlaceMapper.fromDomain(place))
    }

    override suspend fun removePlace(place: Place) {
        placeDao.removePlace(PlaceMapper.fromDomain(place))
    }

}