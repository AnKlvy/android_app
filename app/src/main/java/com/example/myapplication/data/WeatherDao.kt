package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    suspend fun insert(weather: ListItemEntity)

    @Query("SELECT * FROM weather_items")
    suspend fun getAllWeatherItems(): List<ListItemEntity>
}
