package br.com.tsmweb.inventapp.common

interface Router {
    fun showPlaceList()
    fun back()
    fun navigationUp(): Boolean
    fun isInRootScreen(): Boolean
}