package com.kotlin.openweatherapp.cachedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherCacheEntity::class, CurrentWeatherCacheEntity::class, HourlyWeatherCacheEntity::class, DailyWeatherCacheEntity::class], version = 1)
abstract class WeatherCacheDatabase: RoomDatabase() {

    abstract fun weatherDao(): IWeatherDao

    companion object {
//        @Volatile
//        private var instance: WeatherCacheDatabase? = null
        val LOCK = Any()
        val DATABASE_NAME = "weather_database"


//        fun getDB(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also { instance = it }
//
//
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                WeatherCacheDatabase::class.java,
//                "test_database"
//            )
//                .fallbackToDestructiveMigration()
//                .build()
//
    }
}