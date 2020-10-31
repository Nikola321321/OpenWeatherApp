package com.kotlin.openweatherapp.model

import android.app.Person
import com.kotlin.openweatherapp.util.unitconverterfactory.IUnitConverter
import java.lang.reflect.Type
import kotlin.reflect.typeOf

data class Weather(
    var latitude: Double,
    var longitude: Double,
    var timezone: String,
    var timezoneOffset: String,
    var currentWeather: CurrentWeather,
    var hourlyWeather: List<HourlyWeather>,
    var dailyWeather: List<DailyWeather>
)

data class CurrentWeather(
    var time: Int,
    var sunrise: Int,
    var sunset: Int,
    var temperatureCurrent: Double,
    var feelsLikeTempCurrent: Double,
    var pressure: Int,
    var humidity: Int,
    var weatherDescription: List<WeatherDescription>
)

data class WeatherDescription(
    var id: Int,
    var mainDescription: String,
    var description: String,
    var icon: String
)

data class HourlyWeather(
    var time: Int,
    var temperatureHourly: Double,
    var feelsLikeTempHourly: Double,
    var pressure: Int,
    var humidity: Int,
    var weatherDescription: List<WeatherDescription>
)

data class DailyWeather(
    var time: Int,
    var sunrise: Int,
    var sunset: Int,
    var temperatureDaily: Temperature,
    var feelsLikeTemp: FeelsLikeTemperature,
    var pressure: Int,
    var humidity: Int,
    var weatherDescription: List<WeatherDescription>
)

data class Temperature(
    var day: Double,
    var night: Double,
    var evening: Double,
    var morning: Double,
    var min: Double,
    var max: Double
)

data class FeelsLikeTemperature(
    var day: Double,
    var night: Double,
    var evening: Double,
    var morning: Double
)
