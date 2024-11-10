package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.ListItemEntity
import com.example.myapplication.data.ListItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : FragmentActivity() {
    private val TAG = "MainActivity"
    private lateinit var repository: ListItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")
        showFragment(MenuFragment()) // Показать меню при создании активности
        // Инициализация базы данных и репозитория
        val db = AppDatabase.getInstance(applicationContext)
        repository = ListItemRepository(db)

        // Пример использования CRUD
        CoroutineScope(Dispatchers.Main).launch()
        {
            // Добавление нового элемента
            val newItem = ListItemEntity(
                title = "New Title",
                subtitle = "New Subtitle",
                date = Date(),
                imageResId = R.drawable.expense_image
            )
            repository.insertItem(newItem)

            // Получение всех элементов
            val items = repository.getAllItems()

            // Получение одного элемента по ID
            val item = repository.getItemById(1)

            // Обновление элемента
            val updatedItem = item?.copy(title = "Updated Title")
            if (updatedItem != null) {
                repository.updateItem(updatedItem)
            }

            // Удаление элемента
            if (item != null) {
                repository.deleteItem(item)
            }

            // Удаление всех элементов
            repository.deleteAllItems()
        }
    }

    fun onItemSelected(item: ListItem) {
        val bundle = Bundle().apply {
            putParcelable("item", item) // Передаем выбранный элемент
        }
        val detailFragment = DetailFragment().apply {
            arguments = bundle
        }
        showFragment(detailFragment) // Показываем DetailFragment
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null) // Добавить текущий фрагмент в стек
        }
    }



    @Composable
    fun MainScreen() {
        val configuration = LocalConfiguration.current
        if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            // Горизонтальная ориентация
            Row(modifier = Modifier.fillMaxSize()) {
                MenuFragment().apply {
                    showFragment(this) // Показываем MenuFragment слева
                }

                DetailFragment().apply {
                    showFragment(this) // Показываем DetailFragment справа
                }
            }
        } else {
            // Вертикальная ориентация
            Column(modifier = Modifier.fillMaxSize()) {
                // Здесь отображаем только одно из меню или деталей в зависимости от выбора
                MenuFragment().apply {
                    showFragment(this) // Показываем MenuFragment
                }
            }
        }
    }
}
