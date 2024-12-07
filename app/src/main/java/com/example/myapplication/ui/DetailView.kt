package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.MenuViewModel

@Composable
fun DetailView(itemId: String, viewModel: MenuViewModel) {
    // Собираем список элементов из StateFlow
    val items = viewModel.items.collectAsState().value
    val item = items.find { it.id.toString() == itemId }

    if (item != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Название города: ${item.name}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Температура: ${item.temp}°C", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Описание: ${item.description}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Дата: ${item.date}", style = MaterialTheme.typography.bodySmall)

            // Добавляем иконку погоды
            item.icon.let {
                val imageUrl = "https://openweathermap.org/img/wn/$it@2x.png"
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(50.dp) // Размер иконки
                )
            }
        }
    } else {
        Text(text = "Элемент не найден")
    }
}
