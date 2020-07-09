package br.com.tsmweb.domain.place.interactor

import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.domain.place.repository.PlaceRepository

class RemovePlaceUseCase(
    private val repository: PlaceRepository
) {
    suspend fun execute(place: Place) {
        repository.removePlace(place)
    }
}