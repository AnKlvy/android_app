package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.ListItem

@Entity(tableName = "list_items")
data class ListItemEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val title: String,
        val subtitle: String,
        val date: Long,
        val imageResId: Int,
        val text: String
) {
        // Преобразование из Entity в UI-модель
        fun toListItem(): ListItem {
                return ListItem(
                        title = title,
                        subtitle = subtitle,
                        date = date,
                        imageResId = imageResId,
                        text = text
                )
        }
}
