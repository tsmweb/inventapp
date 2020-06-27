package br.com.tsmweb.inventapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import br.com.tsmweb.presentation.Router
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)
    }

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    val router: Router by inject { parametersOf(navController) }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration.
            Builder(router.getRootScreens()).
            setDrawerLayout(drawerLayout).
            build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener { item ->
            onNavigationItemSelected(item)
        }
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        drawerLayout.closeDrawers()

        when (item.itemId) {
            R.id.inventoryListFragment ->
                router.showInventoryList()
            R.id.placeListFragment ->
                router.showPlaceList()
            R.id.accountListFragment ->
                router.showAccountList()
            R.id.aboutOption ->
                Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show()
            R.id.logoutOption ->
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (router.isInRootScreen()) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}