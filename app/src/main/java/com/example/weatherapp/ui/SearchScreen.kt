package com.example.weatherapp.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.data.local.FavoriteCity
import com.example.weatherapp.ui.components.WeatherCard
import com.example.weatherapp.viewmodel.HomeViewModel
import com.example.weatherapp.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var cityQuery by remember { mutableStateOf("") }
    val searchResult = searchViewModel.searchResult
    val isLoading = searchViewModel.isLoading
    val favorites = homeViewModel.favoriteCities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = cityQuery,
            onValueChange = {
                cityQuery = it
                searchViewModel.clearSearchResult()
            },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (cityQuery.isNotBlank()) {
                    searchViewModel.searchWeather(cityQuery)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            searchResult != null -> {
                WeatherCard(
                    weather = searchResult,
                    isFavorite = favorites.value.any { it.name == searchResult.name },
                    onToggleFavorite = {
                        val isAlreadyFavorite = favorites.value.any { it.name == searchResult.name }
                        val favoriteCity = FavoriteCity(
                            name = searchResult.name,
                            temp = searchResult.main.temp,
                            description = searchResult.weather.firstOrNull()?.description ?: "",
                            icon = searchResult.weather.firstOrNull()?.icon ?: ""
                        )

                        if (isAlreadyFavorite) {
                            homeViewModel.removeFromFavorites(favoriteCity)
                        } else {
                            homeViewModel.saveToFavorites(searchResult)
                        }
                    }
                )
            }

            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Search for a city to see the weather.")
                }
            }
        }
    }
}
