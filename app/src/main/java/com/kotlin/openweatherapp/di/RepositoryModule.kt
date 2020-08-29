package com.kotlin.openweatherapp.di

import com.kotlin.openweatherapp.cachedb.IWeatherDao
import com.kotlin.openweatherapp.cachedb.mappers.CacheEntityToWeatherMapper
import com.kotlin.openweatherapp.cachedb.mappers.WeatherToCacheEntityMapper
import com.kotlin.openweatherapp.networkservice.IOpenWeatherApi
import com.kotlin.openweatherapp.networkservice.mappers.NetworkResponseMapper
import com.kotlin.openweatherapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        weatherDao: IWeatherDao,
        networkService: IOpenWeatherApi,
        networkResponseMapper: NetworkResponseMapper,
        weatherToCacheEntityMapper: WeatherToCacheEntityMapper,
        cacheEntityToWeatherMapper: CacheEntityToWeatherMapper
    ): Repository {
        return Repository(
            weatherDao,
            networkService,
            networkResponseMapper,
            weatherToCacheEntityMapper,
            cacheEntityToWeatherMapper
        )
    }
}