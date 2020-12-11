package br.com.tsmweb.data.room.inventory.mapper

import android.annotation.SuppressLint
import br.com.tsmweb.data.room.common.Constants.DATE_FORMAT_DB
import br.com.tsmweb.data.room.inventory.entity.InventoryEntity
import br.com.tsmweb.data.room.inventory.entity.InventoryView
import br.com.tsmweb.domain.inventory.model.Inventory
import java.text.SimpleDateFormat

object InventoryMapper {
    fun fromDomain(domain: Inventory) = InventoryEntity(
        id = domain.id,
        localeId = domain.localeId,
        dateInventory = SimpleDateFormat(DATE_FORMAT_DB).format(domain.dateInventory)
    )

    @SuppressLint("SimpleDateFormat")
    fun toDomain(entity: InventoryView) = Inventory(
        id = entity.id,
        localeId = entity.localeId,
        dateInventory = SimpleDateFormat(DATE_FORMAT_DB).parse(entity.dateInventory),
        patrimonyChecked = entity.patrimonyChecked,
        patrimonyUnchecked = entity.patrimonyUnchecked,
        patrimonyNotFound = entity.patrimonyNotFound
    )
}