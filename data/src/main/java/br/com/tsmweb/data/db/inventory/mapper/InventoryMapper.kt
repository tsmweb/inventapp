package br.com.tsmweb.data.db.inventory.mapper

import br.com.tsmweb.data.db.inventory.entity.InventoryEntity
import br.com.tsmweb.domain.inventory.model.Inventory

object InventoryMapper {
    fun fromDomain(domain: Inventory) = InventoryEntity(
        id = domain.id,
        localeId = domain.localeId,
        dateInventory = domain.dateInventory,
        patrimonyChecked = domain.patrimonyChecked,
        patrimonyNotChecked = domain.patrimonyNotChecked,
        patrimonyNotFound = domain.patrimonyNotFound
    )

    fun toDomain(entity: InventoryEntity) = Inventory(
        id = entity.id,
        localeId = entity.localeId,
        dateInventory = entity.dateInventory,
        patrimonyChecked = entity.patrimonyChecked,
        patrimonyNotChecked = entity.patrimonyNotChecked,
        patrimonyNotFound = entity.patrimonyNotFound
    )
}