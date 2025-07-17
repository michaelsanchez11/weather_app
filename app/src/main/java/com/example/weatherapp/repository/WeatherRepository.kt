package com.example.weatherapp.repository

import com.example.weatherapp.data.local.FavoriteCity
import com.example.weatherapp.data.local.FavoriteCityDao
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.remote.WeatherApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApiService,
    private val dao: FavoriteCityDao,
    private val apiKey: String
) {
    suspend fun getCurrentWeather(cityName: String) : WeatherResponse {
        return api.getCurrentWeather(cityName, apiKey)
    }

    suspend fun saveFavoriteCity(city: FavoriteCity) {
        dao.insertFavorite(city)
    }

    suspend fun removeFavorite(city: FavoriteCity) {
        dao.deleteFavorite(city)
    }

    fun getAllFavorites(): Flow<List<FavoriteCity>> {
        return dao.getAllFavorites()
    }
}