package com.kotlin.openweatherapp.cachedb

import androidx.room.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "weather_entity")
class WeatherCacheEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 1,
    var timestamp: String = LocalDateTime.now().toEpochSecond(ZoneOffset.ofTotalSeconds(7200)).toString(),
    var latitude: Double,
    var longitude: Double,
    var timezone: String,
    var timezoneOffset: String
)

@Entity(
    tableName = "current_weather_entity",
    foreignKeys = [ForeignKey(
        entity = WeatherCacheEntity::class,
        parentColumns = ["id"],
        childColumns = ["currentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class CurrentWeatherCacheEntity(
    @PrimaryKey(autoGenerate = false)
    var currentId: Int = 1,
    @ColumnInfo(name = "current_weather_time")
    var time: Int,
    @ColumnInfo(name = "current_weather_sunrise")
    var sunrise: Int,
    @ColumnInfo(name = "current_weather_sunset")
    var sunset: Int,
    var temperatureCurrent: Double,
    var feelsLikeTempCurrent: Double,
    @ColumnInfo(name = "current_weather_pressure")
    var pressure: Int,
    @ColumnInfo(name = "current_weather_humidity")
    var humidity: Int,
    @Embedded(prefix = "current_weather_")
    var weatherDescription: WeatherDescriptionCacheEntity
)

class WeatherDescriptionCacheEntity(
    var id: Int,
    var mainDescription: String,
    var description: String,
    var icon: String
)

@Entity(
    tableName = "hourly_weather_entity",
    foreignKeys = [ForeignKey(
        entity = WeatherCacheEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyParentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class HourlyWeatherCacheEntity(
    var hourlyParentId: Int = 1,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "hourly_weather_time")
    var time: Int,
    var temperatureHourly: Double,
    var feelsLikeTempHourly: Double,
    @ColumnInfo(name = "hourly_weather_pressure")
    var pressure: Int,
    @ColumnInfo(name = "hourly_weather_humidity")
    var humidity: Int,
    @Embedded(prefix = "hourly_weather_")
    var weatherDescription: WeatherDescriptionCacheEntity

)

@Entity(
    tableName = "daily_weather_entity",
    foreignKeys = [ForeignKey(
        entity = WeatherCacheEntity::class,
        parentColumns = ["id"],
        childColumns = ["dailyParentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class DailyWeatherCacheEntity(

    var dailyParentId: Int = 1,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "daily_weather_time")
    var time: Int,
    @ColumnInfo(name = "daily_weather_sunrise")
    var sunrise: Int,
    @ColumnInfo(name = "daily_weather_sunset")
    var sunset: Int,
    @Embedded
    var temperatureDaily: TemperatureCacheEntity,
    @Embedded
    var feelsLikeTemp: FeelsLikeTemperatureCacheEntity,
    @ColumnInfo(name = "daily_weather_pressure")
    var pressure: Int,
    @ColumnInfo(name = "daily_weather_humidity")
    var humidity: Int,
    @Embedded(prefix = "daily_weather_")
    var weatherDescription: WeatherDescriptionCacheEntity
)

class TemperatureCacheEntity(
    @ColumnInfo(name = "temp_day")
    var day: Double,
    @ColumnInfo(name = "temp_night")
    var night: Double,
    @ColumnInfo(name = "temp_evening")
    var evening: Double,
    @ColumnInfo(name = "temp_morning")
    var morning: Double,
    @ColumnInfo(name = "temp_min")
    var min: Double,
    @ColumnInfo(name = "temp_max")
    var max: Double
)

class FeelsLikeTemperatureCacheEntity(
    @ColumnInfo(name = "feels_like_temp_day")
    var day: Double,
    @ColumnInfo(name = "feels_like_temp_night")
    var night: Double,
    @ColumnInfo(name = "feels_like_temp_evening")
    var evening: Double,
    @ColumnInfo(name = "feels_like_temp_morning")
    var morning: Double
)

