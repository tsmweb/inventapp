package br.com.tsmweb.domain.patrimony.interactor

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore
import java.lang.IllegalArgumentException

class SavePatrimonyUseCase(
    private val patrimonyDataStore: PatrimonyDataStore
) {
    suspend fun execute(patrimony: Patrimony) {
        if (patrimonyIsValid(patrimony)) {
            patrimonyDataStore.savePatrimony(patrimony)
        } else {
            throw IllegalArgumentException("Patrimony is invalid")
        }
    }

    private fun patrimonyIsValid(patrimony: Patrimony): Boolean {
        return (patrimony.locale.id.isNotBlank() &&
                patrimony.code.isNotBlank() &&
                patrimony.name.isNotBlank() &&
                patrimony.dependency.isNotBlank())
    }
}