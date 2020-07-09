package br.com.tsmweb.domain.place.interactor

import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class ListPlacesUseCase(
    private val repository: PlaceRepository
) {
    fun execute(term: String): Flow<List<Place>> {
        return repository.loadPlaces(term)
    }
}