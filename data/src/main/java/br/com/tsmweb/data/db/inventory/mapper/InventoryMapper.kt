package br.com.tsmweb.data.db.inventory.mapper

import br.com.tsmweb.data.db.common.Constants.DATE_FORMAT_DB
import br.com.tsmweb.data.db.inventory.entity.InventoryEntity
import br.com.tsmweb.domain.inventory.model.Inventory
import java.text.SimpleDateFormat

object InventoryMapper {
    fun fromDomain(domain: Inventory) = InventoryEntity(
        id = domain.id,
        localeId = domain.localeId,
        dateInventory = SimpleDateFormat(DATE_FORMAT_DB).format(domain.dateInventory),
        patrimonyChecked = domain.patrimonyChecked,
        patrimonyUnchecked = domain.patrimonyUnchecked,
        patrimonyNotFound = domain.patrimonyNotFound
    )

    fun toDomain(entity: InventoryEntity) = Inventory(
        id = entity.id,
        localeId = entity.localeId,
        dateInventory = SimpleDateFormat(DATE_FORMAT_DB).parse(entity.dateInventory),
        patrimonyChecked = entity.patrimonyChecked,
        patrimonyUnchecked = entity.patrimonyUnchecked,
        patrimonyNotFound = entity.patrimonyNotFound
    )
}