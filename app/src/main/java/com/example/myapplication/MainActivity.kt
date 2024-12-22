package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

        val itemsForRoom = listOf(
            ListItemEntity(
                title = "Носок",
                subtitle = "Дырявый",
                date = Calendar.getInstance().timeInMillis,
                imageResId = R.drawable.sock_image,
                text = "Этот носок прошел множество дорог, но, к сожалению, дырки не оставили шансов на его спасение. Возможно, он заслуживает вторую жизнь в роли тряпки для пыли."
            ),
            ListItemEntity(
                title = "Расходы",
                subtitle = "Большие",
                date = Calendar.getInstance().timeInMillis,
                imageResId = R.drawable.expense_image,
                text = "Расходы в этом месяце превысили бюджет. Основные траты пришлись на поездки, покупки и непредвиденные расходы. Пришло время пересмотреть финансовые планы."
            ),
            ListItemEntity(
                title = "Лес",
                subtitle = "С грибами",
                date = Calendar.getInstance().timeInMillis,
                imageResId = R.drawable.forest_image,
                text = "Этот лес, где в тенистых уголках спрятались грибы, будто из сказки. Тихая охота была успешной, но стоит помнить, что грибы нужно собирать с осторожностью."
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

}
