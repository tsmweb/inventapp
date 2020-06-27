package br.com.tsmweb.presentation.place.binding

import br.com.tsmweb.domain.place.model.Place

object PlaceMapper {

    fun fromDomain(domain: Place): PlaceBinding {
        return PlaceBinding(
            code = domain.code,
            name = domain.name,
            lastInventory = domain.lastInventory
        )
    }

    fun toDomain(binding: PlaceBinding): Place {
        return Place(
            code = binding.code,
            name = binding.name,
            lastInventory = binding.lastInventory
        )
    }

}