package br.com.tsmweb.data.db.place.mapper

import br.com.tsmweb.data.db.place.entity.PlaceEntity
import br.com.tsmweb.domain.place.model.Place

object PlaceMapper {
    fun fromDomain(domain: Place) = PlaceEntity(
        id = domain.id,
        code = domain.code,
        name = domain.name,
        lastInventory = domain.lastInventory
    )

    fun toDomain(entity: PlaceEntity) = Place(
        id = entity.id,
        code = entity.code,
        name = entity.name,
        lastInventory = entity.lastInventory
    )
}