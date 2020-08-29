package com.kotlin.openweatherapp.cachedb.entityrelations

import androidx.room.Embedded
import androidx.room.Relation
import com.kotlin.openweatherapp.cachedb.CurrentWeatherCacheEntity

class PrimaryAndCurrentWeatherRelation(
    @Embedded
    val currentWeatherCacheEntity: CurrentWeatherCacheEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    )
    val currentWeather: CurrentWeatherCacheEntity
)