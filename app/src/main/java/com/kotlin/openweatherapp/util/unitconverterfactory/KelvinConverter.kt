package com.kotlin.openweatherapp.util.unitconverterfactory

class KelvinConverter : IUnitConverter {
    override fun convert(value: Double): Int {
        return value.toInt()
    }
}