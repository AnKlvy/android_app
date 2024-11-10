package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface ListItemDao {
    // Создание
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ListItemEntity)

    // Чтение всех элементов
    @Query("SELECT * FROM list_items")
    suspend fun getAllItems(): List<ListItemEntity>

    // Чтение одного элемента по ID
    @Query("SELECT * FROM list_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): ListItemEntity?

    // Обновление элемента
    @Update
    suspend fun updateItem(item: ListItemEntity)

    // Удаление элемента
    @Delete
    suspend fun deleteItem(item: ListItemEntity)

    // Удаление всех элементов
    @Query("DELETE FROM list_items")
    suspend fun deleteAllItems()
}
