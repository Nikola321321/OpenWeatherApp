package com.kotlin.openweatherapp.ui.fragments

import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.kotlin.openweatherapp.MyApplication
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.model.Weather
import com.kotlin.openweatherapp.ui.viewmodels.MainViewModel
import com.kotlin.openweatherapp.util.*
import com.kotlin.openweatherapp.util.Preferences.provideConverter
import com.kotlin.openweatherapp.util.Preferences.setUnitTypePreference
import com.kotlin.openweatherapp.util.Preferences.unitTypeState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var prefs: SharedPreferences

    private val url = "https://openweathermap.org/img/wn/"
    private lateinit var geocoder: Geocoder

    private val prefsListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                LOCATION_TYPE -> {
                    Log.i("SHARED PREFS", "preferenceChangeListener: ${sharedPreferences.getInt(
                        LOCATION_TYPE, 0)} ")
                    mainViewModel.getLocation(
                        locationType = sharedPreferences.getInt(
                            LOCATION_TYPE,
                            LOCATION_TYPE_DEFAULT
                        ),
                        latitude = sharedPreferences.getFloat("City_Lat", 0.0f).toDouble(),
                        longitude = sharedPreferences.getFloat("City_Lon", 0.0f).toDouble()
                    )
                }
               LOCATION_CITY_NAME -> {
                    Log.i("SHARED PREFS", "preferenceChangeListener: ${sharedPreferences.getString(
                        LOCATION_CITY_NAME, "")}")
                    mainViewModel.getLocation(
                        locationType = sharedPreferences.getInt(
                            LOCATION_TYPE,
                            LOCATION_TYPE_DEFAULT
                        ),
                        latitude = sharedPreferences.getFloat(LOCATION_CITY_LAT, 0.0f).toDouble(),
                        longitude = sharedPreferences.getFloat(LOCATION_CITY_LON, 0.0f).toDouble()
                    )
                }
                "list_preference_1" -> {
                    unitTypeState = setUnitTypePreference(prefs.getString("list_preference_1", ""))
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geocoder = Geocoder(this.activity, Locale.getDefault())
        prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.instance)
        preferenceChangeListener()

        subscribeObservers()
        mainViewModel.getLocation(
            prefs.getInt(LOCATION_TYPE, LOCATION_TYPE_DEFAULT),
            prefs.getFloat(LOCATION_CITY_LAT, 0.0f).toDouble(),
            prefs.getFloat(LOCATION_CITY_LON, 0.0f).toDouble()
        )
    }


    private fun subscribeObservers() {
        mainViewModel.weatherData.observe(this.viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<Weather> -> {
                    displayProgressBar(false)
                    displayWeather(dataState.data)

                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun preferenceChangeListener() {
        prefs.registerOnSharedPreferenceChangeListener(prefsListener)
    }

    private fun displayWeather(weather: Weather) {
        val format = DecimalFormat("#.######")
        format.roundingMode = RoundingMode.CEILING
        tv_city_name.text = geocoder.getFromLocation(
            format.format(weather.latitude).toDouble(),
            format.format(weather.longitude).toDouble(),
            1
        )[0].locality

        Log.i("RESOLVE_LOCATION", "displayWeather: ${geocoder.getFromLocation(
            format.format(weather.latitude).toDouble(),
            format.format(weather.longitude).toDouble(),
            1
        )[0].locality} ")
        tv_temperature.text =
            provideConverter(unitTypeState).convert(weather.currentWeather.temperatureCurrent)
                .toString() + "°"

        Picasso.get().load(url + weather.currentWeather.weatherDescription[0].icon + "@2x.png")
            .fit().into(imageView2)

        tv_date_time.text =
            "${TimeConverter.convertTime(weather.currentWeather.time).dayOfWeek}, ${TimeConverter.convertTime(
                weather.currentWeather.time
            ).month} ${TimeConverter.convertTime(weather.currentWeather.time).dayOfMonth}, ${TimeConverter.convertTime(
                weather.currentWeather.time
            ).hour}: ${TimeConverter.convertTime(weather.currentWeather.time).minute}"

        tv_real_feel.text =
            "Real Feel " + provideConverter(unitTypeState).convert(weather.currentWeather.feelsLikeTempCurrent)
                .toString() + " °"

        tv_weather_description.text = weather.currentWeather.weatherDescription[0].description
    }


    private fun displayError(message: String?) {
        if (message != null) {
            tv_temperature.text = message
        } else {
            tv_temperature.text = "error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        pb_progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}