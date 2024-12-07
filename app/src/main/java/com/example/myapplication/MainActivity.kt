package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.WeatherRepository
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.ListItemEntity
import com.example.myapplication.ui.DetailView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherDao = AppDatabase.getDatabase(applicationContext).weatherDao()
        val weatherRepository = WeatherRepository(weatherDao)
        val menuViewModel = ViewModelProvider(this, MenuViewModel.Factory(application)).get(MenuViewModel::class.java)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "menu_screen") {
                composable("menu_screen") {
                    MenuScreen(navController = navController, viewModel = menuViewModel)
                }
                composable("detail_screen/{itemId}") { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
                    DetailView(itemId = itemId, viewModel = menuViewModel)
                }
            }
        }
    }
}

@Composable
fun MenuScreen(navController: NavController, viewModel: MenuViewModel) {
    val items = viewModel.items.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items.forEach { item ->
            MenuItemView(item = item, navController = navController)
        }
    }
}

@Composable
fun MenuItemView(item: ListItemEntity, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("detail_screen/${item.id}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Город: ${item.name}")
    }
}
