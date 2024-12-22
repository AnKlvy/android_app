package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListItemDao {
    @Insert
    suspend fun insert(listItem: ListItemEntity)

    @Query("SELECT * FROM list_items")
    suspend fun getAll(): List<ListItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ListItemEntity>)

    @Query("DELETE FROM list_items")
    suspend fun clearDatabase()
}
