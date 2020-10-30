package br.com.tsmweb.data.db.inventory.entity

import androidx.room.Embedded
import androidx.room.Relation
import br.com.tsmweb.data.db.locale.entity.LocaleEntity

data class InventoryAndLocale(
    @Embedded
    val inventory: InventoryEntity,

    @Relation(
        parentColumn = "locale_id",
        entityColumn = "id"
    )
    val locale: LocaleEntity
)