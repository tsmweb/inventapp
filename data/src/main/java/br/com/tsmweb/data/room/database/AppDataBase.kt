package br.com.tsmweb.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tsmweb.data.room.inventory.dao.InventoryDao
import br.com.tsmweb.data.room.inventory.dao.InventoryItemDao
import br.com.tsmweb.data.room.inventory.entity.InventoryEntity
import br.com.tsmweb.data.room.inventory.entity.InventoryItemEntity
import br.com.tsmweb.data.room.locale.dao.LocaleDao
import br.com.tsmweb.data.room.locale.entity.LocaleEntity
import br.com.tsmweb.data.room.patrimony.dao.PatrimonyDao
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyEntity

@Database(
    entities = [
        LocaleEntity::class,
        PatrimonyEntity::class,
        InventoryEntity::class,
        InventoryItemEntity::class
    ],
    version = 1,
    exportSchema = false)
internal abstract class AppDataBase: RoomDatabase() {

    abstract fun localeDao(): LocaleDao
    abstract fun patrimonyDao(): PatrimonyDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun inventoryItemDao(): InventoryItemDao

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