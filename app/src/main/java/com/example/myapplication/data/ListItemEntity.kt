package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_items")
data class ListItemEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val name: String,
        val temp: Double,
        val description: String,
        val icon: String,
        val date: Long
)
