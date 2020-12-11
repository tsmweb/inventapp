package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.gateway.LocaleDataStore
import kotlinx.coroutines.flow.Flow

class ViewLocaleDetailsUseCase(
    private val dataStore: LocaleDataStore
) {
    fun execute(id: String): Flow<Locale?> {
        return dataStore.loadLocale(id)
    }
}