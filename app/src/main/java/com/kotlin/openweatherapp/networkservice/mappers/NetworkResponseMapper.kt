package com.kotlin.openweatherapp.networkservice.mappers

import com.kotlin.openweatherapp.model.*
import com.kotlin.openweatherapp.networkservice.*
import com.kotlin.openweatherapp.util.EntityMapper
import javax.inject.Inject

class NetworkResponseMapper
@Inject
constructor() : EntityMapper<WeatherNetworkEntity, Weather> {

    override fun mapFromEntity(entity: WeatherNetworkEntity): Weather {
        return Weather(
            latitude = entity.latitude,
            longitude = entity.longitude,
            timezone = entity.timezone,
            timezoneOffset = entity.timezoneOffset,
            currentWeather = toCurrentWeather(entity.currentWeather),
            hourlyWeather = toHourlyWeather(entity.hourlyWeather),
            dailyWeather = toDailyWeather(entity.dailyWeather)
        )
    }

    override fun mapToEntity(model: Weather): WeatherNetworkEntity {
        return WeatherNetworkEntity(
            latitude = model.latitude,
            longitude = model.longitude,
            timezone = model.timezone,
            timezoneOffset = model.timezoneOffset,
            currentWeather = toCurrentWeatherNetworkEntity(model.currentWeather),
            hourlyWeather = toHourlyWeatherWeatherNetworkEntity(model.hourlyWeather),
            dailyWeather = toDailyWeatherWeatherNetworkEntity(model.dailyWeather)
        )
    }


    private fun toDailyWeatherWeatherNetworkEntity(dailyWeather: List<DailyWeather>): List<DailyWeatherNetworkEntity> {
        val dailyWeatherList = mutableListOf<DailyWeatherNetworkEntity>()
        for (entity in dailyWeather) {
            dailyWeatherList.add(
                DailyWeatherNetworkEntity(
                    time = entity.time,
                    feelsLikeTemp = toFeelsLikeTempNetworkEntity(entity.feelsLikeTemp),
                    humidity = entity.humidity,
                    pressure = entity.pressure,
                    sunset = entity.sunset,
                    sunrise = entity.sunrise,
                    temperatureDaily = toTemperatureNetworkEntity(entity.temperatureDaily),
                    weatherDescription = toWeatherDescriptionNetworkEntity(entity.weatherDescription)
                )
            )
        }
        return dailyWeatherList
    }

    private fun toTemperatureNetworkEntity(temperatureDaily: Temperature): TemperatureNetworkEntity {
        return TemperatureNetworkEntity(
            day = temperatureDaily.day,
            night = temperatureDaily.night,
            evening = temperatureDaily.evening,
            morning = temperatureDaily.morning,
            min = temperatureDaily.min,
            max = temperatureDaily.max
        )
    }

    private fun toFeelsLikeTempNetworkEntity(feelsLikeTemp: FeelsLikeTemperature): FeelsLikeTemperatureNetworkEntity {
        return FeelsLikeTemperatureNetworkEntity(
            day = feelsLikeTemp.day,
            night = feelsLikeTemp.night,
            morning = feelsLikeTemp.morning,
            evening = feelsLikeTemp.evening
        )
    }

    private fun toHourlyWeatherWeatherNetworkEntity(hourlyWeather: List<HourlyWeather>): List<HourlyWeatherNetworkEntity> {
        val hourlyWeatherList = mutableListOf<HourlyWeatherNetworkEntity>()
        for (entity in hourlyWeather) {
            hourlyWeatherList.add(
                HourlyWeatherNetworkEntity(
                    time = entity.time,
                    pressure = entity.pressure,
                    humidity = entity.humidity,
                    temperatureHourly = entity.temperatureHourly,
                    feelsLikeTempHourly = entity.feelsLikeTempHourly,
                    weatherDescription = toWeatherDescriptionNetworkEntity(entity.weatherDescription)
                )
            )
        }
        return hourlyWeatherList
    }

    private fun toCurrentWeatherNetworkEntity(currentWeather: CurrentWeather): CurrentWeatherNetworkEntity =
        CurrentWeatherNetworkEntity(
            time = currentWeather.time,
            sunrise = currentWeather.sunrise,
            sunset = currentWeather.sunset,
            temperatureCurrent = currentWeather.temperatureCurrent,
            feelsLikeTempCurrent = currentWeather.feelsLikeTempCurrent,
            pressure = currentWeather.pressure,
            humidity = currentWeather.humidity,
            weatherDescription = toWeatherDescriptionNetworkEntity(currentWeather.weatherDescription)
        )


    private fun toCurrentWeather(currentWeatherNetworkEntity: CurrentWeatherNetworkEntity) =
        CurrentWeather(
            time = currentWeatherNetworkEntity.time,
            sunrise = currentWeatherNetworkEntity.sunrise,
            sunset = currentWeatherNetworkEntity.sunset,
            temperatureCurrent = currentWeatherNetworkEntity.temperatureCurrent,
            feelsLikeTempCurrent = currentWeatherNetworkEntity.feelsLikeTempCurrent,
            pressure = currentWeatherNetworkEntity.pressure,
            humidity = currentWeatherNetworkEntity.humidity,
            weatherDescription = toWeatherDescription(currentWeatherNetworkEntity.weatherDescription)
        )

    private fun toWeatherDescriptionNetworkEntity(weatherDescription: List<WeatherDescription>): List<WeatherDescriptionNetworkEntity> {
        val weatherDescriptionList = mutableListOf<WeatherDescriptionNetworkEntity>()

        for (entity in weatherDescription) {
            weatherDescriptionList.add(
                WeatherDescriptionNetworkEntity(
                    id = entity.id,
                    mainDescription = entity.description,
                    description = entity.description,
                    icon = entity.icon
                )
            )
        }
        return weatherDescriptionList
    }

    private fun toWeatherDescription(
        weatherDescriptionNetworkEntity: List<WeatherDescriptionNetworkEntity>
    ): List<WeatherDescription> {
        val weatherDescriptionList = mutableListOf<WeatherDescription>()

        for (entity in weatherDescriptionNetworkEntity) {
            weatherDescriptionList.add(
                WeatherDescription(
                    id = entity.id,
                    mainDescription = entity.description,
                    description = entity.description,
                    icon = entity.icon
                )
            )
        }
        return weatherDescriptionList
    }

    private fun toHourlyWeather(
        hourlyWeatherNetworkEntity: List<HourlyWeatherNetworkEntity>
    ): List<HourlyWeather> {
        val hourlyWeatherList = mutableListOf<HourlyWeather>()
        for (entity in hourlyWeatherNetworkEntity) {
            hourlyWeatherList.add(
                HourlyWeather(
                    time = entity.time,
                    pressure = entity.pressure,
                    humidity = entity.humidity,
                    temperatureHourly = entity.temperatureHourly,
                    feelsLikeTempHourly = entity.feelsLikeTempHourly,
                    weatherDescription = toWeatherDescription(entity.weatherDescription)
                )
            )
        }
        return hourlyWeatherList
    }

    private fun toDailyWeather(
        dailyWeatherNetworkEntity: List<DailyWeatherNetworkEntity>
    ): List<DailyWeather> {
        val dailyWeatherList = mutableListOf<DailyWeather>()
        for (entity in dailyWeatherNetworkEntity) {
            dailyWeatherList.add(
                DailyWeather(
                    time = entity.time,
                    feelsLikeTemp = toFeelsLikeTemp(entity.feelsLikeTemp),
                    humidity = entity.humidity,
                    pressure = entity.pressure,
                    sunset = entity.sunset,
                    sunrise = entity.sunrise,
                    temperatureDaily = toTemperature(entity.temperatureDaily),
                    weatherDescription = toWeatherDescription(entity.weatherDescription)
                )
            )
        }
        return dailyWeatherList
    }

    private fun toFeelsLikeTemp(feelsLikeTemperatureNetworkEntity: FeelsLikeTemperatureNetworkEntity): FeelsLikeTemperature {
        return FeelsLikeTemperature(
            day = feelsLikeTemperatureNetworkEntity.day,
            night = feelsLikeTemperatureNetworkEntity.night,
            morning = feelsLikeTemperatureNetworkEntity.morning,
            evening = feelsLikeTemperatureNetworkEntity.evening
        )
    }

    private fun toTemperature(temperatureNetworkEntity: TemperatureNetworkEntity): Temperature {
        return Temperature(
            day = temperatureNetworkEntity.day,
            night = temperatureNetworkEntity.night,
            evening = temperatureNetworkEntity.evening,
            morning = temperatureNetworkEntity.morning,
            min = temperatureNetworkEntity.min,
            max = temperatureNetworkEntity.max
        )
    }

}
