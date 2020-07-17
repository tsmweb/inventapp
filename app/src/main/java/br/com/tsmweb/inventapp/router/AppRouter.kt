package br.com.tsmweb.inventapp.router

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Router

class AppRouter(
    activity: FragmentActivity
): Router {

    private val navController: NavController by lazy {
        Navigation.findNavController(activity, R.id.navHostFragment)
    }
    private val rootScreens = setOf(R.id.localeListFragment)

    init {
        val appBarConfiguration = AppBarConfiguration.Builder(rootScreens).build()

        if (activity is AppCompatActivity) {
            NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration)
        }
    }

    override fun showLocaleList() {
        navController.navigate(R.id.localeListFragment)
    }

    override fun back() {
        navController.popBackStack()
    }

    override fun navigationUp(): Boolean {
        return navController.navigateUp()
    }

    override fun isInRootScreen(): Boolean {
        return rootScreens.contains(navController.currentDestination?.id)
    }

}