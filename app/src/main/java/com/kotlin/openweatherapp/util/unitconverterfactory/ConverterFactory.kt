package com.kotlin.openweatherapp.util.unitconverterfactory

class ConverterFactory {

    companion object{
        fun create(type: UnitType): IUnitConverter{
            return when(type){
                UnitType.CELSIUS -> CelsiusConverter()
                UnitType.FAHRENHEIT -> FahrenheitConverter()
                UnitType.DEFAULT -> KelvinConverter()
            }
        }
    }
}