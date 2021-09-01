package br.com.tsmweb.data.room.locale

import androidx.room.withTransaction
import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.locale.mapper.LocaleMapper
import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.gateway.LocaleDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class RoomLocaleDataStore(
    private val db: AppDataBase
): LocaleDataStore {

    private val localeDao = db.localeDao()
    private val patrimonyDao = db.patrimonyDao()
    private val inventoryDao = db.inventoryDao()
    private val inventoryItemDao = db.inventoryItemDao()

    override fun loadLocales(term: String): Flow<List<Locale>> {
        val tm = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return localeDao.loadLocales(tm)
            .map { places -> places.map(LocaleMapper::toDomain) }
    }

    override fun loadLocale(id: String): Flow<Locale?> {
        return localeDao.loadLocale(id)
            .map { placeEntity -> LocaleMapper.toDomain(placeEntity) }
    }

    override suspend fun saveLocale(locale: Locale) {
        if (locale.id.isBlank()) {
            locale.id = UUID.randomUUID().toString()
        }

        localeDao.saveLocale(LocaleMapper.fromDomain(locale))
    }

    override suspend fun removeLocale(locale: Locale) {
        db.withTransaction {
            inventoryItemDao.removeInventoryItemByLocale(locale.id)
            inventoryDao.removeInventoryByLocale(locale.id)
            patrimonyDao.removePatrimonyByLocale(locale.id)
            localeDao.removeLocale(LocaleMapper.fromDomain(locale))
        }
    }

}