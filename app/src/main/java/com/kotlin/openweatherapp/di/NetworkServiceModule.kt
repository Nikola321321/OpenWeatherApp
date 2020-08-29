package com.kotlin.openweatherapp.di

import com.kotlin.openweatherapp.cachedb.WeatherCacheDatabase
import com.kotlin.openweatherapp.networkservice.IOpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkServiceModule {


    @Singleton
    @Provides
    fun provideNetworkServiceBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideNetworkService(networkServiceBuilder: Retrofit.Builder): IOpenWeatherApi{
        return networkServiceBuilder
            .build()
            .create(IOpenWeatherApi::class.java)
    }
}