package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.WeatherRepository
import com.example.myapplication.data.api.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> get() = _weatherList

    private val cities = listOf("Almaty", "Astana", "Shymkent", "Aktobe", "Karaganda")
    private val apiKey = "3d0f72f6326df877a80136db612913f3"

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            // Получаем и сохраняем данные в базу
            weatherRepository.fetchAndSaveWeatherData(cities, apiKey)

            // Загружаем сохраненные данные из базы данных и обновляем список
            _weatherList.value = weatherRepository.getWeatherFromDatabase()
        }
    }
}
