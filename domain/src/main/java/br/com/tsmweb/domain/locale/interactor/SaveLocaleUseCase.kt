package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.gateway.LocaleDataStore
import java.lang.IllegalArgumentException

class SaveLocaleUseCase(
    private val localeDataStore: LocaleDataStore
) {
    suspend fun execute(locale: Locale) {
        if (localeIsValid(locale)) {
            localeDataStore.saveLocale(locale)
        } else {
            throw IllegalArgumentException("Locale is invalid")
        }
    }

    private fun localeIsValid(locale: Locale): Boolean {
        return (
            locale.code.isNotBlank() &&
            locale.name.isNotBlank()
        )
    }
}