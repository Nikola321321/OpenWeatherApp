package com.kotlin.openweatherapp.cachedb.mappers

import com.kotlin.openweatherapp.cachedb.*
import com.kotlin.openweatherapp.model.*
import javax.inject.Inject

class CacheEntityToWeatherMapper
@Inject
constructor() {

    fun mapToWeather(
        weatherCacheEntity: WeatherCacheEntity,
        currentWeatherCacheEntity: CurrentWeatherCacheEntity,
        hourlyWeatherCacheEntity: List<HourlyWeatherCacheEntity>,
        dailyWeatherCacheEntity: List<DailyWeatherCacheEntity>
    ): Weather {
        return Weather(
            latitude = weatherCacheEntity.latitude,
            longitude = weatherCacheEntity.longitude,
            timezone = weatherCacheEntity.timezone,
            timezoneOffset = weatherCacheEntity.timezoneOffset,
            currentWeather = toCurrentWeather(currentWeatherCacheEntity),
            hourlyWeather = toHourlyWeather(hourlyWeatherCacheEntity),
            dailyWeather = toDailyWeather(dailyWeatherCacheEntity)
        )
    }

    private fun toDailyWeather(dailyWeatherCacheEntity: List<DailyWeatherCacheEntity>): List<DailyWeather> {
        var list = mutableListOf<DailyWeather>()
        dailyWeatherCacheEntity.map {
            list.add(
                DailyWeather(
                    humidity = it.humidity,
                    pressure = it.pressure,
                    time = it.time,
                    sunset = it.sunset,
                    sunrise = it.sunrise,
                    temperatureDaily = toTemperature(it.temperatureDaily),
                    feelsLikeTemp = toFeelsLikeTemperature(it.feelsLikeTemp),
                    weatherDescription = toCurrentWeatherDescription(it.weatherDescription)
                )
            )
        }
        return list
    }

    private fun toFeelsLikeTemperature(feelsLikeTemp: FeelsLikeTemperatureCacheEntity): FeelsLikeTemperature {
        return FeelsLikeTemperature(
            day = feelsLikeTemp.day,
            evening = feelsLikeTemp.evening,
            morning = feelsLikeTemp.morning,
            night = feelsLikeTemp.night
        )
    }

    private fun toTemperature(temperatureDaily: TemperatureCacheEntity): Temperature {
        return Temperature(
            day = temperatureDaily.day,
            night = temperatureDaily.night,
            morning = temperatureDaily.morning,
            evening = temperatureDaily.evening,
            max = temperatureDaily.max,
            min = temperatureDaily.min
        )
    }


    private fun toHourlyWeather(hourlyWeatherCacheEntity: List<HourlyWeatherCacheEntity>): List<HourlyWeather> {
        var list = mutableListOf<HourlyWeather>()
        hourlyWeatherCacheEntity.map {
            list.add(
                HourlyWeather(
                    time = it.time,
                    pressure = it.pressure,
                    humidity = it.humidity,
                    feelsLikeTempHourly = it.feelsLikeTempHourly,
                    temperatureHourly = it.temperatureHourly,
                    weatherDescription = toCurrentWeatherDescription(it.weatherDescription)
                )
            )
        }
        return list
    }

    private fun toCurrentWeather(currentWeatherCacheEntity: CurrentWeatherCacheEntity): CurrentWeather {
        return CurrentWeather(
            time = currentWeatherCacheEntity.time,
            sunrise = currentWeatherCacheEntity.sunrise,
            sunset = currentWeatherCacheEntity.sunset,
            humidity = currentWeatherCacheEntity.humidity,
            pressure = currentWeatherCacheEntity.pressure,
            temperatureCurrent = currentWeatherCacheEntity.temperatureCurrent,
            feelsLikeTempCurrent = currentWeatherCacheEntity.feelsLikeTempCurrent,
            weatherDescription = toCurrentWeatherDescription(currentWeatherCacheEntity.weatherDescription)
        )
    }

    private fun toCurrentWeatherDescription(weatherDescription: WeatherDescriptionCacheEntity): List<WeatherDescription> {
        return listOf(
            WeatherDescription(
                id = weatherDescription.id,
                icon = weatherDescription.icon,
                mainDescription = weatherDescription.mainDescription,
                description = weatherDescription.description
            )
        )
    }

}