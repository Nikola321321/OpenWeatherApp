package com.kotlin.openweatherapp.ui.viewmodels

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kotlin.openweatherapp.MyApplication
import com.kotlin.openweatherapp.cachedb.ChosenLocation
import com.kotlin.openweatherapp.model.Weather
import com.kotlin.openweatherapp.repository.Repository
import com.kotlin.openweatherapp.util.DataState
import com.kotlin.openweatherapp.util.LOCATION_TYPE_CHOSEN
import com.kotlin.openweatherapp.util.LOCATION_TYPE_CURRENT
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates


class MainViewModel
@ViewModelInject
constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var locationType by Delegates.notNull<Int>()


    private val _weatherData: MutableLiveData<DataState<Weather>> = MutableLiveData()
    val weatherData: LiveData<DataState<Weather>>
        get() = _weatherData


    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(MyApplication.instance)
    }

    private val locationRequest = LocationRequest().apply {
        interval = 3000
        fastestInterval = 3000
        numUpdates = 1
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            retrieveForecast(p0!!.lastLocation.latitude, p0.lastLocation.longitude)

        }
    }


    @SuppressLint("MissingPermission")
    fun getLocation(locationType: Int, latitude: Double, longitude: Double) {
        this.locationType = locationType

        when (locationType) {
            LOCATION_TYPE_CHOSEN -> {
                retrieveForecast(latitude, longitude)
            }
            LOCATION_TYPE_CURRENT -> {
                viewModelScope.launch {
                    if (fusedLocationProviderClient.locationAvailability.await().isLocationAvailable) {
                        val location: Location = fusedLocationProviderClient.lastLocation.await()
                        retrieveForecast(location.latitude, location.longitude)
                    } else {
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest, locationCallback,
                            Looper.myLooper()
                        )
                    }
                }
            }
        }
    }

    private fun retrieveForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.getWeather(latitude, longitude)
                .map {
                    _weatherData.value = it
                }.launchIn(viewModelScope)

        }
    }

    fun insertChosenLocation(chosenLocation: ChosenLocation) {
        viewModelScope.launch {
            repository.insertChosenLocation(chosenLocation)
        }
    }

    fun getChosenLocations(): LiveData<List<ChosenLocation>> {
       return repository.getChosenLocations()
    }

    fun deleteChosenLocation(chosenLocation: ChosenLocation) {
        viewModelScope.launch {
            repository.deleteChosenLocation(chosenLocation)
        }
    }
}








