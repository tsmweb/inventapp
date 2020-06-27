package br.com.tsmweb.domain.place.repository

import br.com.tsmweb.domain.place.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun loadPlaces(term: String): Flow<List<Place>>
}