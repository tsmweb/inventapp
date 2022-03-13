package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.repository.LocaleRepository

class RemoveLocaleUseCase(
    private val repository: LocaleRepository
) {
    suspend fun execute(locale: Locale) {
        repository.removeLocale(locale)
    }
}