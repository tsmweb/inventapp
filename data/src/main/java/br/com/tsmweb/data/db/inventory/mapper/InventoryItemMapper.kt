package br.com.tsmweb.data.db.inventory.mapper

import br.com.tsmweb.data.db.inventory.entity.InventoryItemEntity
import br.com.tsmweb.domain.inventory.model.InventoryItem

object InventoryItemMapper {
    fun fromDomain(domain: InventoryItem) = InventoryItemEntity(
        id = domain.id,
        inventoryId = domain.inventoryId,
        patrimonyCode = domain.patrimonyCode,
        patrimonyName = domain.patrimonyName,
        patrimonyDependency = domain.patrimonyDependency,
        patrimonyStatus = domain.patrimonyStatus,
        status = domain.status,
        note = domain.note
    )

    fun toDomain(entity: InventoryItemEntity) = InventoryItem(
        id = entity.id,
        inventoryId = entity.inventoryId,
        patrimonyCode = entity.patrimonyCode,
        patrimonyName = entity.patrimonyName,
        patrimonyDependency = entity.patrimonyDependency,
        patrimonyStatus = entity.patrimonyStatus,
        status = entity.status,
        note = entity.note
    )
}