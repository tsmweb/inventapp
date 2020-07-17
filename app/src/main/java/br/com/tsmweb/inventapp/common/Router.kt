package br.com.tsmweb.inventapp.common

interface Router {
    fun showLocaleList()
    fun back()
    fun navigationUp(): Boolean
    fun isInRootScreen(): Boolean
}