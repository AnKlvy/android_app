package com.example.myapplication.data

import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.Main
import com.example.myapplication.data.api.Weather
import com.example.myapplication.data.api.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDao: WeatherDao) {

    suspend fun fetchAndSaveWeatherData(cities: List<String>, apiKey: String) {
        val results = cities.mapNotNull { city ->
            try {
                val weatherResponse = ApiClient.weatherApi.getCityWeather(city, apiKey)
                saveWeatherToDatabase(weatherResponse)
                weatherResponse
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun saveWeatherToDatabase(weather: WeatherResponse) {
        withContext(Dispatchers.IO) {
            val weatherEntity = ListItemEntity(
                name = weather.name,
                temp = weather.main.temp,
                description = weather.weather.firstOrNull()?.description ?: "No description",
                icon = weather.weather.firstOrNull()?.icon ?: "",
                date = weather.dt
            )
            weatherDao.insert(weatherEntity)
        }
    }

    suspend fun getWeatherFromDatabase(): List<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            weatherDao.getAllWeatherItems().map { entity ->
                WeatherResponse(
                    name = entity.name,
                    main = Main(temp = entity.temp),
                    weather = listOf(Weather(icon = entity.icon, description = entity.description)),
                    dt = entity.date
                )
            }
        }
    }
}
