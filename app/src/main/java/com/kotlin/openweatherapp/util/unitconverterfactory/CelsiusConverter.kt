package com.kotlin.openweatherapp.util.unitconverterfactory

import com.kotlin.openweatherapp.util.ABSOLUTE_ZERO

class CelsiusConverter : IUnitConverter {
    override fun convert(value: Double): Int {
        val result = value - ABSOLUTE_ZERO
        return result.toInt()
    }
}