package com.kotlin.openweatherapp.cachedb.mappers

import com.kotlin.openweatherapp.cachedb.*
import com.kotlin.openweatherapp.model.DailyWeather
import com.kotlin.openweatherapp.model.HourlyWeather
import com.kotlin.openweatherapp.model.Weather
import javax.inject.Inject

class WeatherToCacheEntityMapper
@Inject
constructor(){

    lateinit var weatherCacheEntity: WeatherCacheEntity
    lateinit var currentWeatherCacheEntity: CurrentWeatherCacheEntity
    lateinit var hourlyWeatherCacheEntity: List<HourlyWeatherCacheEntity>
    lateinit var dailyWeatherCacheEntity: List<DailyWeatherCacheEntity>


    fun mapToCacheEntity(entity: Weather) {
        toWeatherCacheEntity(entity)
        toCurrentWeatherCacheEntity(entity)
        toHourlyWeatherCacheEntity(entity)
        toDailyWeatherCacheEntity(entity)
    }


    private fun toDailyWeatherCacheEntity(entity: Weather) {
        dailyWeatherCacheEntity = entity.dailyWeather.map { it ->
            dailyToDailyCache(it)
        }
    }

    private fun toHourlyWeatherCacheEntity(entity: Weather) {
        hourlyWeatherCacheEntity = entity.hourlyWeather.map { it ->
            hourlyToHourlyCache(it)
        }
    }

    private fun toCurrentWeatherCacheEntity(entity: Weather) {
        currentWeatherCacheEntity =
            CurrentWeatherCacheEntity(
                time = entity.currentWeather.time,
                sunrise = entity.currentWeather.sunrise,
                sunset = entity.currentWeather.sunset,
                temperatureCurrent = entity.currentWeather.temperatureCurrent,
                feelsLikeTempCurrent = entity.currentWeather.feelsLikeTempCurrent,
                pressure = entity.currentWeather.pressure,
                humidity = entity.currentWeather.humidity,
                weatherDescription = WeatherDescriptionCacheEntity(
                    id = entity.currentWeather.weatherDescription[0].id,
                    mainDescription = entity.currentWeather.weatherDescription[0].mainDescription,
                    description = entity.currentWeather.weatherDescription[0].description,
                    icon = entity.currentWeather.weatherDescription[0].icon
                )
            )
    }

    private fun toWeatherCacheEntity(entity: Weather) {
        weatherCacheEntity =
            WeatherCacheEntity(
                latitude = entity.latitude,
                longitude = entity.longitude,
                timezone = entity.timezone,
                timezoneOffset = entity.timezoneOffset
            )
    }

    private fun dailyToDailyCache(dailyWeather: DailyWeather): DailyWeatherCacheEntity {
        return DailyWeatherCacheEntity(
            time = dailyWeather.time,
            sunrise = dailyWeather.sunrise,
            sunset = dailyWeather.sunset,
            temperatureDaily = TemperatureCacheEntity(
                day = dailyWeather.temperatureDaily.day,
                night = dailyWeather.temperatureDaily.night,
                min = dailyWeather.temperatureDaily.min,
                max = dailyWeather.temperatureDaily.max,
                morning = dailyWeather.temperatureDaily.morning,
                evening = dailyWeather.temperatureDaily.evening
            ),
            humidity = dailyWeather.humidity,
            pressure = dailyWeather.pressure,
            weatherDescription = WeatherDescriptionCacheEntity(
                id = dailyWeather.weatherDescription[0].id,
                mainDescription = dailyWeather.weatherDescription[0].mainDescription,
                description = dailyWeather.weatherDescription[0].description,
                icon = dailyWeather.weatherDescription[0].icon
            ),
            feelsLikeTemp = FeelsLikeTemperatureCacheEntity(
                day = dailyWeather.feelsLikeTemp.day,
                evening = dailyWeather.feelsLikeTemp.evening,
                morning = dailyWeather.feelsLikeTemp.morning,
                night = dailyWeather.feelsLikeTemp.night
            )
        )
    }

    private fun hourlyToHourlyCache(hourlyWeather: HourlyWeather): HourlyWeatherCacheEntity {
        return HourlyWeatherCacheEntity(
            time = hourlyWeather.time,
            temperatureHourly = hourlyWeather.temperatureHourly,
            feelsLikeTempHourly = hourlyWeather.feelsLikeTempHourly,
            pressure = hourlyWeather.pressure,
            humidity = hourlyWeather.humidity,
            weatherDescription = WeatherDescriptionCacheEntity(
                id = hourlyWeather.weatherDescription[0].id,
                mainDescription = hourlyWeather.weatherDescription[0].mainDescription,
                description = hourlyWeather.weatherDescription[0].description,
                icon = hourlyWeather.weatherDescription[0].icon
            )
        )
    }

}