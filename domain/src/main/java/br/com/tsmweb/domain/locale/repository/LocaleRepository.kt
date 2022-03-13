package br.com.tsmweb.domain.locale.repository

import br.com.tsmweb.domain.locale.model.Locale
import kotlinx.coroutines.flow.Flow

interface LocaleRepository {
    fun loadLocales(term: String): Flow<List<Locale>>
    fun loadLocale(id: String): Flow<Locale?>
    suspend fun saveLocale(locale: Locale)
    suspend fun removeLocale(locale: Locale)
}