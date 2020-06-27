package br.com.tsmweb.data_room.inventory.mapper

import br.com.tsmweb.data.inventory.model.InventoryEntity
import br.com.tsmweb.data.place.model.PlaceEntity
import br.com.tsmweb.data_room.inventory.entity.InventoryTable
import br.com.tsmweb.data_room.place.entity.PlaceTable

object InventoryMapper {
    fun fromEntity(entity: InventoryEntity): InventoryTable {
        return InventoryTable(
            id = entity.id,
            place = PlaceTable(
                code = entity.place?.code,
                name = entity.place?.name,
                lastInventory = entity.place?.lastInventory),
            dateInventory = entity.dateInventory,
            patrimonyChecked = entity.patrimonyChecked,
            patrimonyNotChecked = entity.patrimonyNotChecked,
            patrimonyNotFound = entity.patrimonyNotFound
        )
    }

    fun toEntity(table: InventoryTable): InventoryEntity {
        return InventoryEntity(
            id = table.id,
            place = PlaceEntity(
                code = table.place?.code,
                name = table.place?.name,
                lastInventory = table.place?.lastInventory),
            dateInventory = table.dateInventory,
            patrimonyChecked = table.patrimonyChecked,
            patrimonyNotChecked = table.patrimonyNotChecked,
            patrimonyNotFound = table.patrimonyNotFound
        )
    }
}