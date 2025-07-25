package com.example.weatherapp.data.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Float,
    val feelsLike: Float,
    val humidity: Int
)

data class Weather(
    val description: String,
    val icon: String
)
