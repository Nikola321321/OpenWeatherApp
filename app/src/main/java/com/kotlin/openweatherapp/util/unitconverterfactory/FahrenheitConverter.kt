package com.kotlin.openweatherapp.util.unitconverterfactory

class FahrenheitConverter: IUnitConverter {
    override fun convert(value: Double): Int {
        val result = value * 9/5 - 459.67
        return result.toInt()
    }
}