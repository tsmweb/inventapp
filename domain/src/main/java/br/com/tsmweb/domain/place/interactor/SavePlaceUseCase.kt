package br.com.tsmweb.domain.place.interactor

import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.domain.place.repository.PlaceRepository
import java.lang.IllegalArgumentException

class SavePlaceUseCase(
    private val repository: PlaceRepository
) {
    suspend fun execute(place: Place) {
        if (placeIsValid(place)) {
            repository.savePlace(place)
        } else {
            throw IllegalArgumentException("Place is invalid")
        }
    }

    private fun placeIsValid(place: Place): Boolean {
        return (
            place.code.isNotBlank() &&
            place.name.isNotBlank()
        )
    }
}