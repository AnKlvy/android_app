package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_items")
data class ListItemEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val title: String,
        val subtitle: String,
        val date: Long, // Хранение даты как long (timestamp)
        val imageResId: Int // Ресурс изображения
)
