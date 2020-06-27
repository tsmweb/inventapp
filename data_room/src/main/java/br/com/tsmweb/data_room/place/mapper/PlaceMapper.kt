package br.com.tsmweb.data_room.place.mapper

import br.com.tsmweb.data.place.model.PlaceEntity
import br.com.tsmweb.data_room.place.entity.PlaceTable

object PlaceMapper {
    fun fromEntity(entity: PlaceEntity): PlaceTable {
        return PlaceTable(
            code = entity.code,
            name = entity.name,
            lastInventory = entity.lastInventory
        )
    }

    fun toEntity(table: PlaceTable): PlaceEntity {
        return PlaceEntity(
            code = table.code,
            name = table.name,
            lastInventory = table.lastInventory
        )
    }
}