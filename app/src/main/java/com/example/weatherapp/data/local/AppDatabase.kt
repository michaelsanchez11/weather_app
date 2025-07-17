package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [FavoriteCity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCityDao() : FavoriteCityDao
}