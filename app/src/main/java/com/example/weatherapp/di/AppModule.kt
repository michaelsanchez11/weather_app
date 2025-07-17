package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.FavoriteCityDao
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiKey(): String = BuildConfig.OPEN_WEATHER_API_KEY

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFavoriteCityDao(db: AppDatabase): FavoriteCityDao {
        return db.favoriteCityDao()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService, favoriteCityDao: FavoriteCityDao, apiKey: String): WeatherRepository {
        return WeatherRepository(api, favoriteCityDao, apiKey)
    }
}