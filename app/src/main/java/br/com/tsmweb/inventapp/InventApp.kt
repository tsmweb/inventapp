package br.com.tsmweb.inventapp

import android.app.Application
import br.com.tsmweb.inventapp.di.appModule
import br.com.tsmweb.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InventApp)
            modules(
                listOf(
                    appModule,
                    presentationModule
                )
            )
        }
    }
}