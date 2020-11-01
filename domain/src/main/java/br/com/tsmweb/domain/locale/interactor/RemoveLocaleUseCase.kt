package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository

class RemoveLocaleUseCase(
    private val localeRepository: LocaleRepository,
    private val patrimonyRepository: PatrimonyRepository
) {
    suspend fun execute(locale: Locale) {
        patrimonyRepository.removePatrimonyByLocale(locale.id)
        localeRepository.removeLocale(locale)
    }
}