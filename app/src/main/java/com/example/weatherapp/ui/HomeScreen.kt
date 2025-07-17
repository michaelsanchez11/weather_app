package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.data.local.FavoriteCity
import com.example.weatherapp.ui.components.WeatherCard
import com.example.weatherapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val weather = viewModel.weatherState
    val isLoading = viewModel.isLoading
    val favorites = viewModel.favoriteCities.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
               viewModel.fetchWeather("London")
            }
        ) {
            Text(text = "Fetch Weather")
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            weather?.let {
                WeatherCard(
                    weather = it,
                    isFavorite = favorites.value.any { fav -> fav.name == it.name},
                    onToggleFavorite = {
                        if (favorites.value.any { fav -> fav.name == it.name}) {
                            viewModel.removeFromFavorites(FavoriteCity(
                                name = it.name,
                                temp = it.main.temp,
                                description = it.weather.firstOrNull()?.description ?: "",
                                icon = it.weather.firstOrNull()?.icon ?: ""
                            ))
                        } else {
                            viewModel.saveToFavorites(it)
                        }
                    }
                    )
            }
        }
    }
}