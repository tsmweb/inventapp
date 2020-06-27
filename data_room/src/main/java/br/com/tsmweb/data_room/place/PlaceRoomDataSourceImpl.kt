package br.com.tsmweb.data_room.place

import br.com.tsmweb.data.place.model.PlaceEntity
import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.data_room.database.AppDataBase
import br.com.tsmweb.data_room.place.mapper.PlaceMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaceRoomDataSourceImpl(
    db: AppDataBase
): PlaceRoomDataSource {

    private val placeDao = db.placeDao()

    override fun loadPlaces(term: String): Flow<List<PlaceEntity>> {
        return placeDao.loadPlaces(term)
            .map { places ->
                places.map(PlaceMapper::toEntity)
            }
    }

}