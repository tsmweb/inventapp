package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.gateway.LocaleDataStore

class RemoveLocaleUseCase(
    private val localeDataStore: LocaleDataStore
) {
    suspend fun execute(locale: Locale) {
        localeDataStore.removeLocale(locale)
    }
}