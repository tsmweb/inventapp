package br.com.tsmweb.domain.patrimony.model

import br.com.tsmweb.domain.locale.model.Locale

class Patrimony(
    var id: Long,
    var locale: Locale,
    var code: String,
    var name: String,
    var dependency: String,
    var status: StatusType
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Patrimony

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}