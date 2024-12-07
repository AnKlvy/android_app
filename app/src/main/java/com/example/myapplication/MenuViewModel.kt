package com.example.myapplication

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.ListItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : ViewModel() {
    private val db = AppDatabase.getDatabase(application)
    private val _items = MutableStateFlow<List<ListItemEntity>>(emptyList())
    val items: StateFlow<List<ListItemEntity>> get() = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            val data = db.weatherDao().getAllWeatherItems()
            _items.value = data
        }
    }

    fun getItemById(itemId: Long): ListItemEntity? {
        return _items.value.find { it.id.toLong() == itemId }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
