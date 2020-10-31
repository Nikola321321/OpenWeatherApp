package com.kotlin.openweatherapp.cachedb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chosen_locations")
class ChosenLocation (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var cityName: String,
    var cityAddress: String,
    var cityLatitude: Double,
    var cityLongitude: Double
) {

}