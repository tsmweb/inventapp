package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import kotlinx.coroutines.flow.Flow

class ViewLocaleDetailsUseCase(
    private val repository: LocaleRepository
) {
    fun execute(id: String): Flow<Locale?> {
        return repository.loadLocale(id)
    }
}