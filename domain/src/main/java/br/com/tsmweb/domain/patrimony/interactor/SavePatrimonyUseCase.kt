package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import java.lang.IllegalArgumentException

class SavePatrimonyUseCase(
    private val repository: PatrimonyRepository
) {
    suspend fun execute(patrimony: Patrimony) {
        if (patrimonyIsValid(patrimony)) {
            repository.savePatrimony(patrimony)
        } else {
            throw IllegalArgumentException("Patrimony is invalid")
        }
    }

    private fun patrimonyIsValid(patrimony: Patrimony): Boolean {
        return (
                patrimony.localeId.isNotBlank() &&
                patrimony.code.isNotBlank() &&
                patrimony.name.isNotBlank() &&
                patrimony.dependency.isNotBlank() &&
                (patrimony.status != null)
        )
    }
}