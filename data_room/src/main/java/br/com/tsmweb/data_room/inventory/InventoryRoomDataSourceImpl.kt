package br.com.tsmweb.data_room.inventory

import br.com.tsmweb.data.inventory.model.InventoryEntity
import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.data_room.database.AppDataBase
import br.com.tsmweb.data_room.inventory.entity.InventoryTable
import br.com.tsmweb.data_room.inventory.mapper.InventoryMapper
import br.com.tsmweb.data_room.place.entity.PlaceTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat

class InventoryRoomDataSourceImpl(
    db: AppDataBase
): InventoryRoomDataSource {

    private val inventoryDao = db.inventoryDao()
    private val placeDao = db.placeDao()

    override fun loadInventories(term: String): Flow<List<InventoryEntity>> {
        val term = if (term.isEmpty() || term.isBlank()) "%" else "%$term%"

        return inventoryDao.loadInventories(term)
            .map { inventories ->
                inventories.map(InventoryMapper::toEntity)
            }
    }

    override suspend fun createInventory(inventory: InventoryEntity) {
        inventoryDao.createInventory(InventoryMapper.fromEntity(inventory))
    }

    override suspend fun removeInventory(inventories: List<InventoryEntity>) {
        val tables = inventories.map(InventoryMapper::fromEntity)
        inventoryDao.removeInventory(*tables.toTypedArray())
    }

    override suspend fun populateInitialData() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        if (placeDao.count() == 0) {
            val quemil = PlaceTable(
                code = "BR 22-4024",
                name = "QUEMIL",
                lastInventory = dateFormat.parse("20/06/2020")
            )

            placeDao.savePlace(quemil)

            for (i in 1..6) {
                val inventory = InventoryTable(
                    id = 0,
                    place = quemil,
                    dateInventory = dateFormat.parse("20/0$i/2020"),
                    patrimonyChecked = 167,
                    patrimonyNotChecked = i,
                    patrimonyNotFound = i-1
                )

                inventoryDao.saveInventory(inventory)
            }
        }
    }

}