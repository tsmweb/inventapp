package br.com.tsmweb.domain.patrimony.model

class Patrimony(
    var id: Long,
    var localeId: String,
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