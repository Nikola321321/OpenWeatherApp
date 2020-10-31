package com.kotlin.openweatherapp.util

import com.kotlin.openweatherapp.util.unitconverterfactory.ConverterFactory
import com.kotlin.openweatherapp.util.unitconverterfactory.IUnitConverter
import com.kotlin.openweatherapp.util.unitconverterfactory.UnitType

object Preferences {

    var unitTypeState = UnitType.CELSIUS

     fun setUnitTypePreference(string: String?): UnitType {
        if (string.equals("Celsius"))
            return UnitType.CELSIUS
        if (string.equals("Fahrenheit"))
            return UnitType.FAHRENHEIT
        if (string.equals("Kelvin"))
            return UnitType.DEFAULT

        return UnitType.CELSIUS
    }

    fun provideConverter(unitType: UnitType): IUnitConverter {
        return when (unitType) {
            UnitType.CELSIUS -> ConverterFactory.create(UnitType.CELSIUS)
            UnitType.FAHRENHEIT -> ConverterFactory.create(UnitType.FAHRENHEIT)
            UnitType.DEFAULT -> ConverterFactory.create(UnitType.DEFAULT)
        }
    }


}