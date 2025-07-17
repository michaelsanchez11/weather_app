package com.example.weatherapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home: Screen("home", "Home", Icons.Default.Home)
    object Favorites: Screen ("favorites", "Favorites", Icons.Default.Favorite)
    object Settings: Screen("settings", "Settings", Icons.Default.Settings)
    object Search: Screen("search", "Search", Icons.Default.Search)
}