package com.kotlin.openweatherapp

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kotlin.openweatherapp.model.Weather
import com.kotlin.openweatherapp.repository.Repository
import com.kotlin.openweatherapp.util.DataState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel
@ViewModelInject
constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _weatherData: MutableLiveData<DataState<Weather>> = MutableLiveData()

    val weatherData: LiveData<DataState<Weather>>
        get() = _weatherData

    fun employLiveData() {
        viewModelScope.launch {
            repository.getWeather()
                .map { dataState ->
                    _weatherData.value = dataState
                }.launchIn(viewModelScope)
        }
    }


}






