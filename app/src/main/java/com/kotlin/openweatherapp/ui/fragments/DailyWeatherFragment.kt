package com.kotlin.openweatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.openweatherapp.R
import com.kotlin.openweatherapp.ui.viewmodels.MainViewModel
import com.kotlin.openweatherapp.ui.adapters.DailyWeatherAdapter
import com.kotlin.openweatherapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_daily_weather.*


@AndroidEntryPoint
class DailyWeatherFragment : Fragment(R.layout.fragment_daily_weather) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_daily_weather.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.weatherData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success -> {
                    rv_daily_weather.adapter = DailyWeatherAdapter(it.data)
                }
            }
        })
    }
}