package br.com.tsmweb.data.db.inventory.mapper

import android.annotation.SuppressLint
import br.com.tsmweb.data.db.common.Constants.DATE_FORMAT_DB
import br.com.tsmweb.data.db.inventory.entity.InventoryAndLocale
import br.com.tsmweb.data.db.inventory.entity.InventoryEntity
import br.com.tsmweb.data.db.locale.mapper.LocaleMapper
import br.com.tsmweb.domain.inventory.model.Inventory
import java.text.SimpleDateFormat

object InventoryMapper {
    fun fromDomain(domain: Inventory) = InventoryEntity(
        id = domain.id,
        localeId = domain.locale.id,
        dateInventory = SimpleDateFormat(DATE_FORMAT_DB).format(domain.dateInventory),
        patrimonyChecked = domain.patrimonyChecked,
        patrimonyUnchecked = domain.patrimonyUnchecked,
        patrimonyNotFound = domain.patrimonyNotFound
    )

    @SuppressLint("SimpleDateFormat")
    fun toDomain(entity: InventoryAndLocale) = Inventory(
        id = entity.inventory.id,
        locale = LocaleMapper.toDomain(entity.locale),
        dateInventory = SimpleDateFormat(DATE_FORMAT_DB).parse(entity.inventory.dateInventory),
        patrimonyChecked = entity.inventory.patrimonyChecked,
        patrimonyUnchecked = entity.inventory.patrimonyUnchecked,
        patrimonyNotFound = entity.inventory.patrimonyNotFound
    )
}