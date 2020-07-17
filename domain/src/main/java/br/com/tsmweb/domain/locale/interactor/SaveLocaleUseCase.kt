package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import java.lang.IllegalArgumentException

class SaveLocaleUseCase(
    private val repository: LocaleRepository
) {
    suspend fun execute(locale: Locale) {
        if (localeIsValid(locale)) {
            repository.saveLocale(locale)
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