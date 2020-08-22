package br.com.tsmweb.data.db.inventory.mapper

import br.com.tsmweb.data.db.inventory.entity.InventoryItemAndPatrimony
import br.com.tsmweb.data.db.inventory.entity.InventoryItemEntity
import br.com.tsmweb.data.db.patrimony.mapper.PatrimonyMapper
import br.com.tsmweb.domain.inventory.model.InventoryItem

object InventoryItemMapper {
    fun fromDomain(domain: InventoryItem) = InventoryItemEntity(
        id = domain.id,
        inventoryId = domain.inventoryId,
        patrimonyId = domain.patrimony.id,
        status = domain.status,
        note = domain.note
    )

    fun toDomain(entity: InventoryItemAndPatrimony) = InventoryItem(
        id = entity.inventoryItem.id,
        inventoryId = entity.inventoryItem.inventoryId,
        patrimony = PatrimonyMapper.toDomain(entity.patrimony),
        status = entity.inventoryItem.status,
        note = entity.inventoryItem.note
    )
}