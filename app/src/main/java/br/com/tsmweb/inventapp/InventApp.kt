package br.com.tsmweb.inventapp

import android.app.Application
import br.com.tsmweb.inventapp.di.*
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
                    localeModule,
                    patrimonyModule,
                    inventoryModule,
                    inventoryItemModule
                )
            )
        }
    }
}