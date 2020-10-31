package com.kotlin.openweatherapp.networkservice

import retrofit2.http.GET
import retrofit2.http.Query

interface IOpenWeatherApi {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
        @Query("appid") appId: String
    ): WeatherNetworkEntity
}