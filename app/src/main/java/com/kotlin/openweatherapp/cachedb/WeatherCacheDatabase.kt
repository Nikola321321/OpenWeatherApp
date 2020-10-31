package com.kotlin.openweatherapp.cachedb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherCacheEntity::class, CurrentWeatherCacheEntity::class, HourlyWeatherCacheEntity::class, DailyWeatherCacheEntity::class, ChosenLocation::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherCacheDatabase : RoomDatabase() {

    abstract fun weatherDao(): IWeatherDao

    companion object {
        val LOCK = Any()
        const val DATABASE_NAME = "weather_database"
    }
}