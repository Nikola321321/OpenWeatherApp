package com.kotlin.openweatherapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlin.openweatherapp.cachedb.IWeatherDao
import com.kotlin.openweatherapp.cachedb.WeatherCacheDatabase
import com.kotlin.openweatherapp.cachedb.WeatherCacheEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object PersistenceModule {


    var INSTANCE: WeatherCacheDatabase? = null

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): WeatherCacheDatabase {
        if (INSTANCE == null) {
            synchronized(WeatherCacheDatabase.LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        WeatherCacheDatabase::class.java,
                        WeatherCacheDatabase.DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    return INSTANCE!!
                }
            }
        }
        return INSTANCE!!
    }

    @Singleton
    @Provides
    fun provideDao(weatherCacheDatabase: WeatherCacheDatabase): IWeatherDao {
        return weatherCacheDatabase.weatherDao()
    }


}


