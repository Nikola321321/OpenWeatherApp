package com.kotlin.openweatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.ui.viewmodels.MainViewModel
import com.kotlin.openweatherapp.ui.adapters.HourlyWeatherAdapter
import com.kotlin.openweatherapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hourly_weather.*

@AndroidEntryPoint
class HourlyWeatherFragment : Fragment(R.layout.fragment_hourly_weather) {

    private val viewModel : MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_hourly_weather.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.weatherData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success -> {
                    val adapter = HourlyWeatherAdapter(it.data)
                    rv_hourly_weather.adapter = adapter
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

}