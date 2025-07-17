package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var searchResult by mutableStateOf<WeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun searchWeather(cityName: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = repository.getCurrentWeather(cityName)
                searchResult = result
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error searching weather", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun clearSearchResult() {
        searchResult = null
    }
}