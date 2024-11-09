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

class MainActivity : FragmentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")
        showFragment(MenuFragment()) // Показать меню при создании активности
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
