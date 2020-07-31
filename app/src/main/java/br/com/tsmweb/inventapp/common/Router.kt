package br.com.tsmweb.inventapp.common

import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding

interface Router {
    fun showLocaleList()
    fun showLocaleForm(locale: LocaleBinding)
    fun showLocaleTabs(locale: LocaleBinding)
    fun showPatrimonyDetails(patrimony: PatrimonyBinding)
    fun showPatrimonyNew(patrimony: PatrimonyBinding)
    fun showPatrimonyEdit(patrimony: PatrimonyBinding)
    fun back()
    fun getRootScreen(): Set<Int>
    fun isInRootScreen(): Boolean
}