package br.com.tsmweb.data.locale.source

import br.com.tsmweb.domain.locale.model.Locale
import kotlinx.coroutines.flow.Flow

interface LocaleRoomDataSource {
    fun loadLocales(term: String): Flow<List<Locale>>
    fun loadLocale(id: String): Flow<Locale?>
    suspend fun saveLocale(locale: Locale)
    suspend fun removeLocale(locale: Locale)
}