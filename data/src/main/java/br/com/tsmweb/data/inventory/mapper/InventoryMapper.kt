package br.com.tsmweb.data.inventory.mapper

import br.com.tsmweb.data.inventory.model.InventoryEntity
import br.com.tsmweb.data.place.model.PlaceEntity
import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.place.model.Place

object InventoryMapper {
    fun fromDomain(domain: Inventory): InventoryEntity {
        return InventoryEntity(
            id = domain.id,
            place = PlaceEntity(domain.place?.code ?: "", domain.place?.name ?: "", domain.place?.lastInventory),
            dateInventory = domain.dateInventory,
            patrimonyChecked = domain.patrimonyChecked,
            patrimonyNotChecked = domain.patrimonyNotChecked,
            patrimonyNotFound = domain.patrimonyNotFound
        )
    }

    fun toDomain(entity: InventoryEntity): Inventory {
        return Inventory(
            id = entity.id,
            place = Place(entity.place?.code ?: "", entity.place?.name ?: "", entity.place?.lastInventory),
            dateInventory = entity.dateInventory,
            patrimonyChecked = entity.patrimonyChecked,
            patrimonyNotChecked = entity.patrimonyNotChecked,
            patrimonyNotFound = entity.patrimonyNotFound
        )
    }
}