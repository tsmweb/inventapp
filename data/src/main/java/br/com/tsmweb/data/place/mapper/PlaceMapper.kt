package br.com.tsmweb.data.place.mapper

import br.com.tsmweb.data.place.model.PlaceEntity
import br.com.tsmweb.domain.place.model.Place

object PlaceMapper {
    fun fromDomain(domain: Place): PlaceEntity {
        return PlaceEntity(
            code = domain.code,
            name = domain.name,
            lastInventory = domain.lastInventory
        )
    }

    fun toDomain(entity: PlaceEntity): Place {
        return Place(
            code = entity.code,
            name = entity.name,
            lastInventory = entity.lastInventory
        )
    }
}