package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.MenuFragment
import com.example.myapplication.DetailFragment

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "menu", modifier = Modifier.fillMaxSize()) {
        composable("menu") {
            MenuFragment() // Это экран меню
        }
        composable("details/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            itemId?.let {
                DetailFragment() // Экран с деталями
            } ?: run {
                Text("Invalid Item")
            }
        }
    }
}
