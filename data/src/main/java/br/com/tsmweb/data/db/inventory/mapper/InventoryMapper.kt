package br.com.tsmweb.data.db.inventory.mapper

import br.com.tsmweb.data.db.inventory.entity.InventoryEntity
import br.com.tsmweb.data.db.place.entity.PlaceEntity
import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.place.model.Place

object InventoryMapper {
    fun fromDomain(domain: Inventory) = InventoryEntity(
        id = domain.id,
        place = PlaceEntity(
            id = domain.place?.id,
            code = domain.place?.code,
            name = domain.place?.name,
            lastInventory = domain.place?.lastInventory
        ),
        dateInventory = domain.dateInventory,
        patrimonyChecked = domain.patrimonyChecked,
        patrimonyNotChecked = domain.patrimonyNotChecked,
        patrimonyNotFound = domain.patrimonyNotFound
    )

    fun toDomain(entity: InventoryEntity) = Inventory(
        id = entity.id,
        place = Place(
            id = entity.place?.id,
            code = entity.place?.code,
            name = entity.place?.name,
            lastInventory = entity.place?.lastInventory),
        dateInventory = entity.dateInventory,
        patrimonyChecked = entity.patrimonyChecked,
        patrimonyNotChecked = entity.patrimonyNotChecked,
        patrimonyNotFound = entity.patrimonyNotFound
    )
}