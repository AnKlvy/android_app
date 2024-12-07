package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> get() = _weatherList

    private val cities = listOf("Almaty", "Astana", "Shymkent", "Aktobe", "Karaganda")
    private val apiKey = "3d0f72f6326df877a80136db612913f3"

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            val results = cities.mapNotNull { city ->
                try {
                    ApiClient.weatherApi.getCityWeather(city, apiKey)
                } catch (e: Exception) {
                    null
                }
            }
            _weatherList.value = results
        }
    }
}
