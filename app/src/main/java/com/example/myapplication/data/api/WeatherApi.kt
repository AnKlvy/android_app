package com.example.myapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val dt: Long // UNIX timestamp
)

data class Main(val temp: Double)
data class Weather(val icon: String, val description: String)

interface WeatherApi {
    @GET("weather")
    suspend fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

object ApiClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}
