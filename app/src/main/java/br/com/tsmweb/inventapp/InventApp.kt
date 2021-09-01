package br.com.tsmweb.inventapp

import android.app.Application
import br.com.tsmweb.data.room.di.DataRoomModules
import br.com.tsmweb.domain.di.DomainModules
import br.com.tsmweb.inventapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@InventApp)
        }

        loadAllModules()
    }

    private fun loadAllModules() {
        DataRoomModules.load()
        DomainModules.load()
        AppModule.load()
    }

}