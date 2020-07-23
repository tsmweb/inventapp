package br.com.tsmweb.inventapp.common

import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding

interface Router {
    fun showLocaleList()
    fun showLocaleTabs(locale: LocaleBinding)
    fun back()
    fun getRootScreen(): Set<Int>
    fun isInRootScreen(): Boolean
}