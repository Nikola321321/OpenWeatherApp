package com.kotlin.openweatherapp.cachedb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IWeatherDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherCacheEntity: WeatherCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherCacheEntity: CurrentWeatherCacheEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(hourlyWeatherCacheEntity: List<HourlyWeatherCacheEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(dailyWeatherCacheEntity: List<DailyWeatherCacheEntity>)

    @Query("Select * from weather_entity")
    suspend fun getWeather(): WeatherCacheEntity

    @Query("Select * from current_weather_entity")
    suspend fun getCurrentWeather(): CurrentWeatherCacheEntity

    @Query("Select * from hourly_weather_entity")
    suspend fun getHourlyWeather(): List<HourlyWeatherCacheEntity>

    @Query("Select * from daily_weather_entity")
    suspend fun getDailyWeather(): List<DailyWeatherCacheEntity>

    @Query("Select timestamp from weather_entity")
    suspend fun getTimeStamp(): String?

    @Delete
    suspend fun deleteWeather(weatherCacheEntity: WeatherCacheEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChosenLocation(chosenLocation: ChosenLocation)

    @Delete
    suspend fun deleteChosenLocation(chosenLocation: ChosenLocation)

    @Query("select * from chosen_locations")
    fun getChosenLocations(): LiveData<List<ChosenLocation>>

}