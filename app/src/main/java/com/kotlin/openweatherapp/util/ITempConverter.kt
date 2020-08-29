package com.kotlin.openweatherapp.util

import com.kotlin.openweatherapp.model.Temperature

interface ITempConverter {
    fun convert(temp: Temperature) : Float;
}