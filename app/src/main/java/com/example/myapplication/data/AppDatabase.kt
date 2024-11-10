package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ListItemEntity::class], version = 1)
@TypeConverters(DateConverter::class) // Добавляем конвертер для Date
abstract class AppDatabase : RoomDatabase() {
    abstract fun listItemDao(): ListItemDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
