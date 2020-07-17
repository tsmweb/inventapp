package br.com.tsmweb.domain.locale.interactor

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import kotlinx.coroutines.flow.Flow

class ListLocalesUseCase(
    private val repository: LocaleRepository
) {
    fun execute(term: String): Flow<List<Locale>> {
        return repository.loadLocales(term)
    }
}