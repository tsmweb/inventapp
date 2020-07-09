package br.com.tsmweb.inventapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tsmweb.inventapp.common.Router
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    val router: Router by inject { parametersOf(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return router.navigationUp()
    }

    override fun onBackPressed() {
        if (router.isInRootScreen()) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}