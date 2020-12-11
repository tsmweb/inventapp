package br.com.tsmweb.data.room.patrimony.entity

import androidx.room.Embedded
import androidx.room.Relation
import br.com.tsmweb.data.room.locale.entity.LocaleEntity

data class PatrimonyAndLocale(

    @Embedded
    val patrimony: PatrimonyEntity,

    @Relation(
        parentColumn = "locale_id",
        entityColumn = "id"
    )
    val locale: LocaleEntity
)