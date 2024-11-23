package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.ListItem
import java.util.Date

@Entity(tableName = "list_items")
data class ListItemEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val title: String,
        val subtitle: String,
        val date: Long, // Хранение даты как long (timestamp)
        val imageResId: Int // Ресурс изображения
) {
        // Преобразование из Entity в UI-модель
        fun toListItem(): ListItem {
                return ListItem(
                        title = title,
                        subtitle = subtitle,
                        date = date,
                        imageResId = imageResId
                )
        }
}
