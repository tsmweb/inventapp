package br.com.tsmweb.domain.place.interactor

import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class ViewPlaceDetailsUseCase(
    private val repository: PlaceRepository
) {
    fun execute(id: String): Flow<Place?> {
        return repository.loadPlace(id)
    }
}