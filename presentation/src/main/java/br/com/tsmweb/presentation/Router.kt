package br.com.tsmweb.presentation

interface Router {
    fun showInventoryList()
    fun showPlaceList()
    fun showAccountList()
    fun back()
    fun isInRootScreen(): Boolean
    fun getRootScreens(): Set<Int>
}