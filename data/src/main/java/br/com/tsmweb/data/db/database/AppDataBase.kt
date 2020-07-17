package br.com.tsmweb.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tsmweb.data.db.locale.dao.LocaleDao
import br.com.tsmweb.data.db.locale.entity.LocaleEntity
import br.com.tsmweb.data.db.patrimony.dao.PatrimonyDao
import br.com.tsmweb.data.db.patrimony.entity.PatrimonyEntity

@Database(
    entities = [LocaleEntity::class, PatrimonyEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun localeDao(): LocaleDao
    abstract fun patrimonyDao(): PatrimonyDao

    companion object {
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "inventAppDB")
                    .build()
            }

            return instance as AppDataBase
        }

        fun destroyInstance() {
            instance = null
        }
    }
}