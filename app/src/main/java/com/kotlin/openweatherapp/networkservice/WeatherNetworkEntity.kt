package com.kotlin.openweatherapp.networkservice

import com.google.gson.annotations.SerializedName

data class WeatherNetworkEntity(
    @SerializedName("lat")
    var latitude: Double,
    @SerializedName("lon")
    var longitude: Double,
    var timezone: String,
    @SerializedName("timezone_offset")
    var timezoneOffset: String,
    @SerializedName("current")
    var currentWeather: CurrentWeatherNetworkEntity,
    @SerializedName("hourly")
    var hourlyWeather: List<HourlyWeatherNetworkEntity>,
    @SerializedName("daily")
    var dailyWeather: List<DailyWeatherNetworkEntity>
)

data class CurrentWeatherNetworkEntity(
    @SerializedName("dt")
    var time: Int,
    var sunrise: Int,
    var sunset: Int,
    @SerializedName("temp")
    var temperatureCurrent: Double,
    @SerializedName("feels_like")
    var feelsLikeTempCurrent: Double,
    val pressure: Int,
    var humidity: Int,


    @SerializedName("weather")
    var weatherDescription: List<WeatherDescriptionNetworkEntity>
)

data class WeatherDescriptionNetworkEntity(
    var id: Int,
    @SerializedName("main")
    var mainDescription: String,
    var description: String,
    var icon: String
)

data class HourlyWeatherNetworkEntity(
    @SerializedName("dt")
    var time: Int,
    @SerializedName("temp")
    var temperatureHourly: Double,
    @SerializedName("feels_like")
    var feelsLikeTempHourly: Double,
    var pressure: Int,
    var humidity: Int,
    @SerializedName("weather")
    var weatherDescription: List<WeatherDescriptionNetworkEntity>
)

data class DailyWeatherNetworkEntity(
    @SerializedName("dt")
    var time: Int,
    var sunrise: Int,
    var sunset: Int,
    @SerializedName("temp")
    var temperatureDaily: TemperatureNetworkEntity,
    @SerializedName("feels_like")
    var feelsLikeTemp: FeelsLikeTemperatureNetworkEntity,
    var pressure: Int,
    var humidity: Int,
    @SerializedName("weather")
    var weatherDescription: List<WeatherDescriptionNetworkEntity>
)

data class TemperatureNetworkEntity(
    var day: Double,
    var night: Double,
    @SerializedName("eve")
    var evening: Double,
    @SerializedName("morn")
    var morning: Double,
    var min: Double,
    var max: Double
)

data class FeelsLikeTemperatureNetworkEntity(
    var day: Double,
    var night: Double,
    @SerializedName("eve")
    var evening: Double,
    @SerializedName("morn")
    var morning: Double
)