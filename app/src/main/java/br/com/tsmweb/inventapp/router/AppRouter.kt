package br.com.tsmweb.inventapp.router

import androidx.navigation.NavController
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.presentation.Router

class AppRouter(
    private val navController: NavController
): Router {

    private val rootScreens = setOf(R.id.inventoryListFragment, R.id.placeListFragment, R.id.accountListFragment)

    override fun showInventoryList() {
        navController.navigate(R.id.inventoryListFragment)
    }

    override fun showPlaceList() {
        navController.navigate(R.id.placeListFragment)
    }

    override fun showAccountList() {
        navController.navigate(R.id.accountListFragment)
    }

    override fun back() {
        navController.popBackStack()
    }

    override fun isInRootScreen(): Boolean {
        return rootScreens.contains(navController.currentDestination?.id)
    }

    override fun getRootScreens(): Set<Int> {
        return rootScreens
    }
}