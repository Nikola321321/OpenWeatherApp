package com.kotlin.openweatherapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.openweatherapp.MainViewModel
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.model.Weather
import com.kotlin.openweatherapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blank.*

@AndroidEntryPoint
class BlankFragment : Fragment(R.layout.fragment_blank) {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        mainViewModel.employLiveData()

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

    private fun displayWeather(weather: Weather) {
        tv_city_name.text = weather.timezone
        tv_temperature.text = weather.currentWeather.temperatureCurrent.toString()
        tv_temp_min.text = weather.dailyWeather[0].temperatureDaily.min.toString()
        tv_temp_max.text = weather.dailyWeather[0].temperatureDaily.max.toString()
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