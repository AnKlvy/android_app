package com.example.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListItemRepository(private val db: AppDatabase) {

    suspend fun insertItem(item: ListItemEntity) {
        withContext(Dispatchers.IO) {
            db.listItemDao().insertItem(item)
        }
    }

    suspend fun getAllItems(): List<ListItemEntity> {
        return withContext(Dispatchers.IO) {
            db.listItemDao().getAllItems()
        }
    }

    suspend fun getItemById(id: Int): ListItemEntity? {
        return withContext(Dispatchers.IO) {
            db.listItemDao().getItemById(id)
        }
    }

    suspend fun updateItem(item: ListItemEntity) {
        withContext(Dispatchers.IO) {
            db.listItemDao().updateItem(item)
        }
    }

    suspend fun deleteItem(item: ListItemEntity) {
        withContext(Dispatchers.IO) {
            db.listItemDao().deleteItem(item)
        }
    }

    suspend fun deleteAllItems() {
        withContext(Dispatchers.IO) {
            db.listItemDao().deleteAllItems()
        }
    }
}
