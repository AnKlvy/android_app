package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.myapplication.viewmodel.WeatherViewModel
import com.example.myapplication.data.api.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
//    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WeatherScreen() // Здесь отображаем экран с погодой
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherList = viewModel.weatherList.collectAsState(initial = emptyList())
// Логирование для проверки данных
    Log.d(TAG, "Weather data: ${weatherList.value}")
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Используем itemsIndexed для работы с индексами
        itemsIndexed(weatherList.value) { index, weather ->
            WeatherItem(weather) // Отображаем каждый элемент погоды
        }
    }
}




@Composable
fun WeatherItem(weather: WeatherResponse) {
    val iconUrl = "https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@2x.png"
    val formattedDate = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(weather.dt * 1000))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(iconUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = weather.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                Text(text = weather.weather.firstOrNull()?.description ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Температура: ${weather.main.temp}°C", style = MaterialTheme.typography.bodyMedium)
                Text(text = formattedDate, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
