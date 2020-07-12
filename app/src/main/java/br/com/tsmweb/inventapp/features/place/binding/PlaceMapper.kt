package br.com.tsmweb.inventapp.features.place.binding

import br.com.tsmweb.domain.place.model.Place

object PlaceMapper {

    fun fromDomain(domain: Place) = PlaceBinding(
        id = domain.id,
        code = domain.code,
        name = domain.name,
        amountPatrimony = domain.amountPatrimony,
        lastInventory = domain.lastInventory
    )

    fun toDomain(binding: PlaceBinding) = Place(
        id = binding.id,
        code = binding.code,
        name = binding.name,
        amountPatrimony = binding.amountPatrimony,
        lastInventory = binding.lastInventory
    )

}