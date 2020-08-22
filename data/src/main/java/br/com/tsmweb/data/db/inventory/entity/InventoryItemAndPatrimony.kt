package br.com.tsmweb.data.db.inventory.entity

import androidx.room.Embedded
import androidx.room.Relation
import br.com.tsmweb.data.db.patrimony.entity.PatrimonyEntity

data class InventoryItemAndPatrimony(
    @Embedded
    val inventoryItem: InventoryItemEntity,
    @Relation(
        parentColumn = "patrimony_id",
        entityColumn = "id"
    )
    val patrimony: PatrimonyEntity
)