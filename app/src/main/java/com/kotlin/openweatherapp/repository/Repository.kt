package com.kotlin.openweatherapp.repository

import com.kotlin.openweatherapp.cachedb.IWeatherDao
import com.kotlin.openweatherapp.cachedb.mappers.CacheEntityToWeatherMapper
import com.kotlin.openweatherapp.cachedb.mappers.WeatherToCacheEntityMapper
import com.kotlin.openweatherapp.model.Weather
import com.kotlin.openweatherapp.networkservice.IOpenWeatherApi
import com.kotlin.openweatherapp.networkservice.mappers.NetworkResponseMapper
import com.kotlin.openweatherapp.util.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDateTime
import java.time.ZoneOffset

class Repository
constructor(
    private val weatherDao: IWeatherDao,
    private val networkService: IOpenWeatherApi,
    private val networkResponseMapper: NetworkResponseMapper,
    private val weatherToCacheEntityMapper: WeatherToCacheEntityMapper,
    private val cacheEntityToWeatherMapper: CacheEntityToWeatherMapper
) {


    suspend fun getWeather(): Flow<DataState<Weather>> = flow {
        emit(DataState.Loading)
        if (enoughTimePassed(
                weatherDao.getTimeStamp()?.toLong()
            ) || weatherDao.getTimeStamp() == null
        ) {
            try {
                val response = networkService.getWeather(
                    "44.78",
                    "20.45",
                    "",
                    "d90d155430df14104e67a3d74fea635b"
                )
                val responseToWeather = networkResponseMapper.mapFromEntity(response)
                weatherToCacheEntityMapper.mapToCacheEntity(responseToWeather)
                persistWeather(weatherToCacheEntityMapper)
                emit(DataState.Success(retrieveWeather()))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        } else {
            try {
                emit(DataState.Success(retrieveWeather()))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }

        }
    }

    private fun enoughTimePassed(dbTime: Long?): Boolean {
        val limit = 3600L
        val currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.ofTotalSeconds(7200))
        if (dbTime != null) {
            if (currentTime - dbTime > limit) {
                return true
            }
        }
        return false
    }

    private suspend fun retrieveWeather(): Weather {
        return cacheEntityToWeatherMapper.mapToWeather(
            weatherDao.getWeather(),
            weatherDao.getCurrentWeather(),
            weatherDao.getHourlyWeather(),
            weatherDao.getDailyWeather()
        )
    }


    private suspend fun persistWeather(entityMapper: WeatherToCacheEntityMapper) {
        weatherDao.insert(entityMapper.weatherCacheEntity)
        weatherDao.insertCurrentWeather(entityMapper.currentWeatherCacheEntity)
        weatherDao.insertDailyWeather(entityMapper.dailyWeatherCacheEntity)
        weatherDao.insertHourlyWeather(entityMapper.hourlyWeatherCacheEntity)
    }

}