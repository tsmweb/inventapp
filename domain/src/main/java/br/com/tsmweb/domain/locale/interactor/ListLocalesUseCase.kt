package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.gateway.LocaleDataStore
import kotlinx.coroutines.flow.Flow

class ListLocalesUseCase(
    private val localeDataStore: LocaleDataStore
) {
    fun execute(term: String): Flow<List<Locale>> {
        return localeDataStore.loadLocales(term)
    }
}