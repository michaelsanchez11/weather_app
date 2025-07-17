package com.example.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse

@Entity(tableName = "favorite_cities")
data class FavoriteCity(
    @PrimaryKey val name: String,
    val temp: Float,
    val description: String,
    val icon: String
)

// ✅ For saving API response to Room
fun WeatherResponse.toFavoriteCity(): FavoriteCity {
    return FavoriteCity(
        name = this.name,
        temp = this.main.temp,
        description = this.weather.firstOrNull()?.description ?: "",
        icon = this.weather.firstOrNull()?.icon ?: ""
    )
}

// ✅ For displaying Room data as WeatherCard
fun FavoriteCity.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        name = this.name,
        main = Main(
            temp = this.temp,
            feelsLike = this.temp, // fallback
            humidity = 0 // fallback
        ),
        weather = listOf(
            Weather(
                description = this.description,
                icon = this.icon
            )
        )
    )
}