package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.ListItemEntity
import com.example.myapplication.data.PreferencesHelper
import kotlinx.coroutines.launch
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")
        showFragment(MenuFragment()) // Показать меню при создании активности

        // Инициализация базы данных и SharedPreferences
        val db = AppDatabase.getDatabase(this)
        val preferencesHelper = PreferencesHelper(this)

        // Пример сохранения примитивных данных в SharedPreferences
        preferencesHelper.savePassword("my_password")
        preferencesHelper.saveDarkThemeEnabled(true)
        preferencesHelper.saveObjectCount(5)

        // Логирование данных в SharedPreferences
        Log.d(TAG, "Password saved: ${preferencesHelper.getPassword()}")
        Log.d(TAG, "Dark Theme Enabled: ${preferencesHelper.isDarkThemeEnabled()}")
        Log.d(TAG, "Object count: ${preferencesHelper.getObjectCount()}")

        // Пример работы с Room (сохранение данных)
        // Преобразование списка ListItem в список ListItemEntity для записи в Room
        val itemsForRoom = listOf(
            ListItemEntity(
                title = "Носок",
                subtitle = "Дырявый",
                date = Calendar.getInstance().timeInMillis,
                imageResId = R.drawable.sock_image
            ),
            ListItemEntity(
                title = "Расходы",
                subtitle = "Большие",
                date = Calendar.getInstance().timeInMillis,
                imageResId = R.drawable.expense_image
            ),
            ListItemEntity(
                title = "Лес",
                subtitle = "С грибами",
                date = Calendar.getInstance().timeInMillis,
                imageResId = R.drawable.forest_image
            )
        )


        lifecycleScope.launch {
            // Сохранение данных в базу
            db.listItemDao().insertAll(itemsForRoom) // Сохраняем весь список
            Log.d(TAG, "Items saved to database: $itemsForRoom")

            // Извлечение данных из базы
            val items = db.listItemDao().getAll()
            // Логирование всех элементов из базы данных
            Log.d(TAG, "Items from database: ${items.joinToString { it.title }}")
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
