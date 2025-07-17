package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.FavoriteCity
import com.example.weatherapp.data.local.toFavoriteCity
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var weatherState by mutableStateOf<WeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = repository.getCurrentWeather(cityName)
                weatherState = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching weather", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun saveToFavorites(weather: WeatherResponse) {
        viewModelScope.launch {
            val favorite = weather.toFavoriteCity()
            repository.saveFavoriteCity(favorite)
        }
    }

    fun removeFromFavorites(city: FavoriteCity) {
        viewModelScope.launch {
            repository.removeFavorite(city)
        }
    }

    val favoriteCities = repository.getAllFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}