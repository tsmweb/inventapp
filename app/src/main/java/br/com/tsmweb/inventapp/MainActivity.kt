package br.com.tsmweb.inventapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil.setContentView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    val router: Router by inject { parametersOf(navController) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

}