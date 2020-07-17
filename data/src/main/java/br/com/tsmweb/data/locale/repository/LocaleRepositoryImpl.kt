package br.com.tsmweb.data.locale.repository

import br.com.tsmweb.data.locale.source.LocaleRoomDataSource
import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import kotlinx.coroutines.flow.Flow

class LocaleRepositoryImpl(
    private val localeRoomDataSource: LocaleRoomDataSource
): LocaleRepository {

    override fun loadLocales(term: String): Flow<List<Locale>> {
        return localeRoomDataSource.loadLocales(term)
    }

    override fun loadLocale(id: String): Flow<Locale?> {
        return localeRoomDataSource.loadLocale(id)
    }

    override suspend fun saveLocale(locale: Locale) {
        localeRoomDataSource.saveLocale(locale)
    }

    override suspend fun removeLocale(locale: Locale) {
        localeRoomDataSource.removeLocale(locale)
    }

}