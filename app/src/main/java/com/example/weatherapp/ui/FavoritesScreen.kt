package com.example.weatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.data.local.FavoriteCity
import com.example.weatherapp.data.local.toWeatherResponse
import com.example.weatherapp.ui.components.WeatherCard
import com.example.weatherapp.viewmodel.HomeViewModel

@Composable
fun FavoritesScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val favoritesState = viewModel.favoriteCities.collectAsState()
    val favorites = favoritesState.value // Should be List<FavoriteCity>

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No favorite cities yet.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = favorites,
                key = { it.name }
            ) { city: FavoriteCity ->
                WeatherCard(
                    weather = city.toWeatherResponse(), // ✅ No more red
                    isFavorite = true,
                    onToggleFavorite = {
                        viewModel.removeFromFavorites(city)
                    }
                )
            }
        }
    }
}
