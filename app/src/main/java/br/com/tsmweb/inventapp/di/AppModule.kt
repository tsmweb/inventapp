package br.com.tsmweb.inventapp.di

import androidx.navigation.NavController
import br.com.tsmweb.inventapp.router.AppRouter
import br.com.tsmweb.presentation.Router
import org.koin.dsl.module

val appModule = module {

    factory { (navController: NavController) ->
        AppRouter(navController) as Router
    }

}